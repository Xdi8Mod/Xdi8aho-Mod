package top.xdi8.mod.firefly8.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import org.apache.commons.lang3.tuple.Pair;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.xdi8.mod.firefly8.block.FireflyBlocks;
import top.xdi8.mod.firefly8.block.Xdi8ahoPortalTopBlock;
import top.xdi8.mod.firefly8.ext.IServerPlayerWithHiddenInventory;
import top.xdi8.mod.firefly8.item.FireflyItems;
import top.xdi8.mod.firefly8.world.Xdi8DimensionUtils;

import java.util.Objects;

@Mixin(ServerPlayer.class)
abstract class ServerPlayerWithHiddenInventoryImpl extends Player implements IServerPlayerWithHiddenInventory {
    @SuppressWarnings("all")private ServerPlayerWithHiddenInventoryImpl() {super(null, null, 0, null);}

    private /*@Nullable*/ ResourceKey<Level> xdi8$portalDimension;
    private BlockPos xdi8$portalPosition = BlockPos.ZERO;

    @Inject(at = @At("RETURN"), method = "readAdditionalSaveData")
    private void readNbt(CompoundTag pCompound, CallbackInfo ci) {
        if (pCompound.contains("firefly8:portalData", 10)) {
            var tag = pCompound.getCompound("firefly8:portalData");
            xdi8$portalDimension = ResourceKey.create(
                    Registry.DIMENSION_REGISTRY, new ResourceLocation(tag.getString("Dimension")));
            BlockPos.CODEC.decode(NbtOps.INSTANCE, tag.get("Pos"))
                    .resultOrPartial(IServerPlayerWithHiddenInventory.LOGGER::error)
                    .ifPresentOrElse(p -> xdi8$portalPosition = p.getFirst(),
                            () -> xdi8$portalDimension = null);
        }
    }

    @Inject(at = @At("RETURN"), method = "addAdditionalSaveData")
    private void writeNbt(CompoundTag pCompound, CallbackInfo ci) {
        if (xdi8$portalDimension != null) {
            var tag = new CompoundTag();
            pCompound.put("firefly8:portalData", tag);
            tag.putString("Dimension", xdi8$portalDimension.location().toString());
            BlockPos.CODEC.encodeStart(NbtOps.INSTANCE, xdi8$portalPosition)
                    .resultOrPartial(IServerPlayerWithHiddenInventory.LOGGER::error)
                    .ifPresent(t -> tag.put("Pos", t));
        }
    }

    @Override
    public Pair<ResourceKey<Level>, BlockPos> xdi8$getPortal() {
        return Pair.of(xdi8$portalDimension, xdi8$portalPosition);
    }

    @Override
    public void xdi8$setPortal(ResourceKey<Level> dimension, BlockPos pos) {
        xdi8$portalDimension = dimension;
        xdi8$portalPosition = pos;
    }

    @Override
    public boolean xdi8$moveItemsToPortal() {
        if (!Xdi8DimensionUtils.canRedirectRespawn(getLevel())) return false;
        if (!xdi8$validatePortal()) return false;
        final SimpleContainer inv = this.xdi8$getPortalInv();
        xdi8$dropOldThings(inv);
        final LazyOptional<IItemHandler> capability = this.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        capability.ifPresent(itemHandler -> {
            for (int i = 0; i < itemHandler.getSlots(); i++) {
                final ItemStack itemStack = itemHandler.extractItem(i, Integer.MAX_VALUE, false);
                if (!itemStack.isEmpty()) {
                    inv.addItem(itemStack);
                }
            }
        });
        return true;
    }

    private void xdi8$dropOldThings(SimpleContainer inv) {
        Level level = Objects.requireNonNull(getServer()).getLevel(xdi8$portalDimension);
        assert level != null;
        Vec3 vec3 = Vec3.atCenterOf(xdi8$portalPosition).add(0, 1, 0);
        ListTag listTag = inv.createTag();
        inv.clearContent();

        var compound = new CompoundTag();
        compound.put("StoredItems", listTag);
        final ItemStack itemStack = new ItemStack(FireflyItems.BUNDLER.get(), 1, compound);
        ItemEntity itemEntity = new ItemEntity(level, vec3.x(), vec3.y(), vec3.z(), itemStack);
        level.addFreshEntity(itemEntity);
    }
    
    public boolean xdi8$validatePortal(ResourceKey<Level> dimension, BlockPos pos) {
        final MinecraftServer server = getServer();
        assert server != null;  // I'm sure
        final ServerLevel level = server.getLevel(dimension);
        if (level == null) return false;
        final BlockState state = level.getBlockState(pos);
        return state.is(FireflyBlocks.XDI8AHO_PORTAL_TOP_BLOCK.get()) &&
                state.getValue(Xdi8ahoPortalTopBlock.FIREFLY_COUNT) > 0;
    }

    public boolean xdi8$validatePortal() {
        return xdi8$validatePortal(xdi8$portalDimension, xdi8$portalPosition);
    }
}
