package org.featurehouse.mcmod.spm.platform.api.network;

import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.slf4j.Logger;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.*;

final class PlayChannelImpl implements PlayChannel {
    private final ResourceLocation id;
    private final int version;
    private final int subversion;
    private final SimpleChannel forgeChannel;

    public PlayChannelImpl(ResourceLocation id, int version, int subversion) {
        this.id = id;
        this.version = version;
        this.subversion = subversion;
        Predicate<String> p = s -> s.split("u", 2)[0].equals(Integer.toString(version));
        this.forgeChannel = NetworkRegistry.newSimpleChannel(id, () -> version + "u" + subversion, p, p);
    }

    @Override
    public <M extends PlayPacket> void registerC2S(int id, Class<M> packet, Function<FriendlyByteBuf, M> decoder) {
        forgeChannel.registerMessage(id, packet, PlayPacket::toPacket, decoder,
                ofConsumer(), Optional.of(NetworkDirection.PLAY_TO_SERVER));
    }

    @Override
    public <M extends PlayPacket> void registerS2C(int id, Class<M> packet, Function<FriendlyByteBuf,  M> decoder) {
        forgeChannel.registerMessage(id, packet, PlayPacket::toPacket, decoder,
                ofConsumer(), Optional.of(NetworkDirection.PLAY_TO_CLIENT));
    }

    @SuppressWarnings("deprecation")
    private <M extends PlayPacket> BiConsumer<M, Supplier<NetworkEvent.Context>> ofConsumer() {
        return (msg, ctx) -> msg.accept(new PlayNetworkEnvironment(
                Optional.ofNullable(ctx.get().getSender()),
                r -> ctx.get().enqueueWork(r),
                () -> ctx.get().setPacketHandled(true)
        ));
    }

    @Override
    public NoArgPlayPacket registerC2S(int id, Consumer<PlayNetworkEnvironment> lambda) {
        NoArgPlayPacket instance = createLambdaPacket(lambda);
        this.registerC2S(id, instance.getClass().asSubclass(PlayPacket.class), buf -> instance);
        noArgPacketIdCacheMap.put(id, instance);
        return instance;
    }

    @Override
    public NoArgPlayPacket registerS2C(int id, Consumer<PlayNetworkEnvironment> lambda) {
        NoArgPlayPacket instance = createLambdaPacket(lambda);
        this.registerS2C(id, instance.getClass().asSubclass(PlayPacket.class), buf -> instance);
        noArgPacketIdCacheMap.put(id, instance);
        return instance;
    }

    @Override
    public void sendC2S(PlayPacket packet) {
        forgeChannel.send(PacketDistributor.SERVER.noArg(), packet);
    }

    @Override
    public void sendC2S(int id) throws IllegalArgumentException {
        sendC2S(getPacketOrThrow(id));
    }

    @Override
    public void sendS2CPlayer(PlayPacket packet, Supplier<ServerPlayer> supplier) {
        forgeChannel.send(PacketDistributor.PLAYER.with(supplier), packet);
    }

    @Override
    public void sendS2CPlayer(int id, Supplier<ServerPlayer> playerSup) throws IllegalArgumentException {
        sendS2CPlayer(getPacketOrThrow(id), playerSup);
    }

    @Override
    public ResourceLocation id() {
        return id;
    }

    public int version() {
        return version;
    }

    public int subversion() {
        return subversion;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (PlayChannelImpl) obj;
        return Objects.equals(this.id, that.id) &&
                this.version == that.version &&
                this.subversion == that.subversion;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, version, subversion);
    }

    @Override
    public String toString() {
        return "PlayChannelImpl[" +
                "id=" + id + ", " +
                "version=" + version + ", " +
                "subversion=" + subversion + ']';
    }

    private final Int2ObjectMap<NoArgPlayPacket> noArgPacketIdCacheMap = new Int2ObjectOpenHashMap<>();
    private NoArgPlayPacket getPacketOrThrow(int id) {
        return Optional.ofNullable(noArgPacketIdCacheMap.get(id))
                .orElseThrow(() -> new IllegalArgumentException("Bad ID: " + id + ". It doesn't exist," +
                        " or is not a NoArgPlayPacket."));
    }

    private static NoArgPlayPacket createLambdaPacket(Consumer<PlayNetworkEnvironment> consumer) {
        Class<? extends NoArgPlayPacket> clazz = null;
        try {
            clazz = createClass();
            var c = MethodHandles.lookup().findConstructor(clazz, MethodType.methodType(void.class, Consumer.class));
            return (NoArgPlayPacket) c.invokeExact(consumer);
        } catch (Throwable t) {
            if (clazz != null)
                LOGGER.error("Bad lambda packet inner class found" + clazz, t);
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

        m = cw.visitMethod(Opcodes.ACC_PUBLIC, "accept",
                '(' + Type.getDescriptor(PlayNetworkEnvironment.class) + ")V",
                null, null);
        m.visitVarInsn(Opcodes.ALOAD, 0);
        m.visitFieldInsn(Opcodes.GETFIELD, className, "c", "Ljava/util/function/Consumer;");
        m.visitMethodInsn(Opcodes.INVOKEINTERFACE, "java/util/function/Consumer", "accept",
                "(Ljava/lang/Object;)V", true);
        m.visitInsn(Opcodes.RETURN);
        m.visitMaxs(-1, -1);

        cw.visitSource(null, "ASM Generated");
        return MethodHandles.lookup().defineClass(cw.toByteArray()).asSubclass(NoArgPlayPacket.class);
    }

    private static final AtomicInteger AI = new AtomicInteger();
    private static final Logger LOGGER = LogUtils.getLogger();
    synchronized private static String genClassName() {
        return Type.getInternalName(NoArgPlayPacketWrapper.class) + "$$asm-gen$" + AI.getAndAdd(1);
    }
}
