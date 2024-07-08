package org.featurehouse.mcmod.spm.platform.api.network;

import com.mojang.logging.LogUtils;
import dev.architectury.networking.NetworkChannel;
import dev.architectury.networking.NetworkManager;
import dev.architectury.platform.Platform;
import dev.architectury.utils.Env;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.slf4j.Logger;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

final class PlayChannelImpl implements PlayChannel {
    private final ResourceLocation id;
    private final NetworkChannel universalChannel;

    public PlayChannelImpl(ResourceLocation id) {
        this.id = id;
        this.universalChannel = NetworkChannel.create(id);
    }

    @Override
    public <M extends PlayPacket> void registerC2S(Class<M> packet, Function<FriendlyByteBuf, M> decoder) {
        universalChannel.register(packet, PlayPacket::toPacket, decoder, handleByEnv(Env.SERVER));
    }

    @Override
    public <M extends PlayPacket> void registerS2C(Class<M> packet, Function<FriendlyByteBuf,  M> decoder) {
        universalChannel.register(packet, PlayPacket::toPacket, decoder, handleByEnv(Env.CLIENT));
    }

    @Override
    public NoArgPlayPacket registerC2S(int id, Consumer<NetworkManager.PacketContext> lambda) {
        NoArgPlayPacket instance = createLambdaPacket(lambda);
        this.registerC2S(instance.getClass().asSubclass(PlayPacket.class), buf -> instance);
        noArgPacketIdCacheMap.put(id, instance);
        return instance;
    }

    @Override
    public NoArgPlayPacket registerS2C(int id, Consumer<NetworkManager.PacketContext> lambda) {
        NoArgPlayPacket instance = createLambdaPacket(lambda);
        this.registerS2C(instance.getClass().asSubclass(PlayPacket.class), buf -> instance);
        noArgPacketIdCacheMap.put(id, instance);
        return instance;
    }

    @Override
    public void sendC2S(PlayPacket packet) {
        if (Platform.getEnvironment() == Env.SERVER) throw new UnsupportedOperationException();
        universalChannel.sendToServer(packet);
    }

    @Override
    public void sendC2S(int id) throws IllegalArgumentException {
        sendC2S(getPacketOrThrow(id));
    }

    @Override
    public void sendS2CPlayer(PlayPacket packet, Supplier<ServerPlayer> supplier) {
        universalChannel.sendToPlayer(supplier.get(), packet);
    }

    @Override
    public void sendS2CPlayer(int id, Supplier<ServerPlayer> playerSup) throws IllegalArgumentException {
        sendS2CPlayer(getPacketOrThrow(id), playerSup);
    }

    @Override
    public ResourceLocation id() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (PlayChannelImpl) obj;
        return Objects.equals(this.id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "PlayChannelImpl[" +
                "id=" + id + ']';
    }

    private final Int2ObjectMap<NoArgPlayPacket> noArgPacketIdCacheMap = new Int2ObjectOpenHashMap<>();
    private NoArgPlayPacket getPacketOrThrow(int id) {
        return Optional.ofNullable(noArgPacketIdCacheMap.get(id))
                .orElseThrow(() -> new IllegalArgumentException("Bad ID: " + id + ". It doesn't exist," +
                        " or is not a NoArgPlayPacket."));
    }

    private static NoArgPlayPacket createLambdaPacket(Consumer<NetworkManager.PacketContext> consumer) {
        Class<? extends NoArgPlayPacket> clazz = null;
        try {
            clazz = createClass();
            var c = MethodHandles.lookup().findConstructor(clazz, MethodType.methodType(void.class, Consumer.class));
            return (NoArgPlayPacket) c.invoke(consumer);
        } catch (Throwable t) {
            if (clazz != null)
                LOGGER.error("Bad lambda packet inner class found: {}", clazz, t);
            else
                LOGGER.error("Bad lambda packet inner class found. (Unknown class)", t);
            return (AlternativePlayPacketWrapper) consumer::accept;
        }
    }

    private static Class<? extends NoArgPlayPacket> createClass() throws IllegalAccessException {
        ClassWriter cw = new ClassWriter(3);
        final String className = genClassName();
        cw.visit(Opcodes.V1_8, Opcodes.ACC_SUPER + Opcodes.ACC_SYNTHETIC, className, null,
                "java/lang/Object", new String[] { Type.getInternalName(NoArgPlayPacketWrapper.class) });
        cw.visitField(Opcodes.ACC_FINAL, "c", "Ljava/util/function/Consumer;", null, null);
        var m = cw.visitMethod(0, "<init>", "(Ljava/util/function/Consumer;)V",
                null, null);
        m.visitVarInsn(Opcodes.ALOAD, 0);
        m.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
        m.visitVarInsn(Opcodes.ALOAD, 0);
        m.visitVarInsn(Opcodes.ALOAD, 1);
        m.visitFieldInsn(Opcodes.PUTFIELD, className, "c", "Ljava/util/function/Consumer;");
        m.visitInsn(Opcodes.RETURN);
        m.visitMaxs(-1, -1);

        m = cw.visitMethod(Opcodes.ACC_PUBLIC, "handle",
                '(' + Type.getDescriptor(NetworkManager.PacketContext.class) + ")V",
                null, null);
        m.visitVarInsn(Opcodes.ALOAD, 0);
        m.visitFieldInsn(Opcodes.GETFIELD, className, "c", "Ljava/util/function/Consumer;");
        m.visitVarInsn(Opcodes.ALOAD, 1);
        m.visitMethodInsn(Opcodes.INVOKEINTERFACE, "java/util/function/Consumer", "accept",
                "(Ljava/lang/Object;)V", true);
        m.visitInsn(Opcodes.RETURN);
        m.visitMaxs(-1, -1);

        cw.visitSource(null, "ASM Generated");
        return MethodHandles.lookup().defineClass(cw.toByteArray()).asSubclass(NoArgPlayPacket.class);
    }

    private static final AtomicInteger AI = new AtomicInteger();
    private static final Logger LOGGER = LogUtils.getLogger();
    private static String genClassName() {  // we just trust our atomic integer
        return Type.getInternalName(NoArgPlayPacketWrapper.class) + "$$asm-gen$" + AI.getAndAdd(1);
    }

    private static <M extends PlayPacket> BiConsumer<M, Supplier<NetworkManager.PacketContext>> handleByEnv(Env directionEnv) {
        return (m, packetContextSupplier) -> {
            var ctx = packetContextSupplier.get();
            if (ctx.getEnvironment() == directionEnv) m.handle(ctx);
        };
    }
}
