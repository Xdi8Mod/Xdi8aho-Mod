package top.xdi8.mod.firefly8.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.commons.lang3.tuple.Pair;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.xdi8.mod.firefly8.block.FireflyBlocks;
import top.xdi8.mod.firefly8.block.Xdi8ahoPortalTopBlock;
import top.xdi8.mod.firefly8.ext.IPlayerWithHiddenInventory;

@Mixin(ServerPlayer.class)
abstract class PlayerWithHiddenInventoryImpl extends Player implements IPlayerWithHiddenInventory {
    @SuppressWarnings("all")private PlayerWithHiddenInventoryImpl() {super(null, null, 0, null);}

    private /*@Nullable*/ ResourceKey<Level> xdi8$portalDimension;
    private BlockPos xdi8$portalPosition = BlockPos.ZERO;

    @Inject(at = @At("RETURN"), method = "readAdditionalSaveData")
    private void readNbt(CompoundTag pCompound, CallbackInfo ci) {
        if (pCompound.contains("firefly8:portalData", 10)) {
            var tag = pCompound.getCompound("firefly8:portalData");
            xdi8$portalDimension = ResourceKey.create(
                    Registry.DIMENSION_REGISTRY, new ResourceLocation(tag.getString("Dimension")));
            BlockPos.CODEC.decode(NbtOps.INSTANCE, tag.get("Pos"))
                    .resultOrPartial(IPlayerWithHiddenInventory.LOGGER::error)
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
                    .resultOrPartial(IPlayerWithHiddenInventory.LOGGER::error)
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
        if (!validatePortal()) return false;
        throw new org.apache.commons.lang3.NotImplementedException("TODO: Move Inventory");
    }

    private boolean validatePortal() {
        final MinecraftServer server = getServer();
        assert server != null;  // I'm sure
        final ServerLevel level = server.getLevel(xdi8$portalDimension);
        if (level == null) return false;
        final BlockState state = level.getBlockState(xdi8$portalPosition);
        return state.is(FireflyBlocks.XDI8AHO_PORTAL_TOP_BLOCK.get()) &&
                state.getValue(Xdi8ahoPortalTopBlock.IS_ACTIVATED);
    }
}
