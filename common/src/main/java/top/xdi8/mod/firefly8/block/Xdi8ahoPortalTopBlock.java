package top.xdi8.mod.firefly8.block;

import io.github.qwerty770.mcmod.xdi8.util.tick.ITickable;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;
import top.xdi8.mod.firefly8.block.entity.FireflyBlockEntityTypes;
import top.xdi8.mod.firefly8.block.entity.PortalTopBlockEntity;
import top.xdi8.mod.firefly8.block.structure.Xdi8PortalBasicData;
import top.xdi8.mod.firefly8.ext.IPlayerWithHiddenInventory;
import top.xdi8.mod.firefly8.ext.IServerPlayerWithHiddenInventory;
import top.xdi8.mod.firefly8.item.FireflyItems;
import top.xdi8.mod.firefly8.item.tint.TintedFireflyBottleItem;
import top.xdi8.mod.firefly8.screen.TakeOnlyChestMenu;

public class Xdi8ahoPortalTopBlock extends BaseEntityBlock {
    public static final int PORTAL_MIN_HEIGHT = 2, PORTAL_MAX_HEIGHT = 16;
    public static final int MAX_FIREFLY_COUNT = 5;
    public static final IntegerProperty FIREFLY_COUNT =
            IntegerProperty.create("fireflies", 0, MAX_FIREFLY_COUNT);

    public Xdi8ahoPortalTopBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(FIREFLY_COUNT, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FIREFLY_COUNT);
    }

    /** When placing the torchlight, checking the base & the pillar */
    public static PortalTopBlockEntity.PortalStatus getPortalHeight(BlockGetter level, BlockPos thisPos, boolean checkBasement) {
        int heightCount = 1;
        int status = PortalTopBlockEntity.PortalStatus.ofStateStatus(level.getBlockState(thisPos.below()));
        if (status == PortalTopBlockEntity.PortalStatus.UNACTIVATED)
            return PortalTopBlockEntity.PortalStatus.empty();
        for (int y = thisPos.getY() - 2 ;; y--) {
            if (heightCount > PORTAL_MAX_HEIGHT) return PortalTopBlockEntity.PortalStatus.empty();

            final BlockPos thatPos = thisPos.atY(y);
            final BlockState blockState = level.getBlockState(thatPos);
            if (blockState.is(FireflyBlockTags.PORTAL_CORE)) {
                if (heightCount < PORTAL_MIN_HEIGHT) return PortalTopBlockEntity.PortalStatus.empty();
                return !checkBasement || isPortalBaseValid(level, thatPos) ?
                        new PortalTopBlockEntity.PortalStatus(status, heightCount) :
                        PortalTopBlockEntity.PortalStatus.empty();
            } else {
                status = PortalTopBlockEntity.PortalStatus.mix(status, PortalTopBlockEntity.PortalStatus.ofStateStatus(blockState));
                if (status != PortalTopBlockEntity.PortalStatus.UNACTIVATED) heightCount++;
                else return PortalTopBlockEntity.PortalStatus.empty();
            }
        }
    }

    private static boolean isPortalBaseValid(BlockGetter level, BlockPos corePos) {
        return Xdi8PortalBasicData.getInstance().fits(level, corePos);
    }

    public static void fillPortalBlocks(Level level, BlockPos topPos,
                                         @Range(from = PORTAL_MIN_HEIGHT, to = PORTAL_MAX_HEIGHT)
                                         int portalHeight) {
        final int minY = topPos.getY() - portalHeight;
        for (int y = topPos.getY() - 1; y >= minY; y--) {
            BlockPos walkPos = topPos.atY(y);
            level.setBlockAndUpdate(walkPos, FireflyBlocks.XDI8AHO_PORTAL_BLOCK.get().defaultBlockState());
        }
    }

    @Override
    public void onPlace(@NotNull BlockState pState, @NotNull Level pLevel,
                        @NotNull BlockPos pPos, @NotNull BlockState pOldState, boolean pIsMoving) {
        if (pLevel.isClientSide()) return;
        if (pState.getValue(FIREFLY_COUNT) <= 0) return;
        processPortal(pLevel, pPos, true);
    }

    public static void processPortal(Level level, BlockPos pos, boolean checkBasement) {
        final var portalHeight = getPortalHeight(level, pos, checkBasement);
        switch (portalHeight.status()) {
            case PortalTopBlockEntity.PortalStatus.UNACTIVATED ->
                    removePortal(level, pos);
            case PortalTopBlockEntity.PortalStatus.READY ->
                    fillPortalBlocks(level, pos, portalHeight.height());
        }
    }

    @Override
    public void onRemove(@NotNull BlockState pState, @NotNull Level pLevel,
                         @NotNull BlockPos pPos, @NotNull BlockState pNewState, boolean pIsMoving) {
        // invalid portal
        if (!pNewState.is(pState.getBlock())) {
            removePortal(pLevel, pPos);
        }
    }

    public static void removePortal(@NotNull Level level, @NotNull BlockPos pos) {
        final int minY = pos.getY() - PORTAL_MAX_HEIGHT;
        for (int y = pos.getY() - 1; y >= minY; y--) {
            BlockPos walkPos = pos.atY(y);
            if (level.getBlockState(walkPos).is(FireflyBlocks.XDI8AHO_PORTAL_BLOCK.get())) {
                level.setBlockAndUpdate(walkPos, Blocks.AIR.defaultBlockState());
            } else break;
        }
    }

    @Override
    public PortalTopBlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return new PortalTopBlockEntity(pPos, pState);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        return level.isClientSide ? null : createTickerHelper(type, FireflyBlockEntityTypes.PORTAL_TOP.get(), ITickable::iTick);
    }

    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    @NotNull
    public InteractionResult use(@NotNull BlockState pState, @NotNull Level pLevel,
                                 @NotNull BlockPos pPos, @NotNull Player pPlayer,
                                 @NotNull InteractionHand pHand, @NotNull BlockHitResult pHit) {
        if (!pLevel.isClientSide()) {
            final int fireflyCount = pState.getValue(FIREFLY_COUNT);
            if (fireflyCount < MAX_FIREFLY_COUNT) {
                ItemStack stack = pPlayer.getItemInHand(pHand);
                if (!stack.is(FireflyItems.TINTED_FIREFLY_BOTTLE.get()) && !stack.is(FireflyItems.FIREFLY_SPAWN_EGG.get())) {
                    stack = pPlayer.getItemInHand(oppositeHand(pHand));
                }
                if (stack.is(FireflyItems.TINTED_FIREFLY_BOTTLE.get())) {
                    if (TintedFireflyBottleItem.removeFirefly(stack)) {
                        pLevel.setBlockAndUpdate(pPos, pState.setValue(FIREFLY_COUNT, fireflyCount + 1));
                        pLevel.playSound(null, pPos, SoundEvents.AMETHYST_CLUSTER_PLACE, SoundSource.BLOCKS, 1.0f, 1.0f);
                        processPortal(pLevel, pPos, false);
                        return InteractionResult.CONSUME;
                    }
                } else if (stack.is(FireflyItems.FIREFLY_SPAWN_EGG.get())) {
                    pLevel.setBlockAndUpdate(pPos, pState.setValue(FIREFLY_COUNT, fireflyCount + 1));
                    pLevel.playSound(null, pPos, SoundEvents.AMETHYST_CLUSTER_PLACE, SoundSource.BLOCKS, 1.0f, 1.0f);
                    processPortal(pLevel, pPos, false);
                    return InteractionResult.CONSUME;
                }
            }

            if (((IServerPlayerWithHiddenInventory)pPlayer).xdi8$validatePortal()) {
                pPlayer.openMenu(menuProvider());
                return InteractionResult.CONSUME;
            } else return InteractionResult.PASS;
        }
        return InteractionResult.SUCCESS;
    }

    @NotNull
    private MenuProvider menuProvider() {
        return new MenuProvider() {
            @Override
            public @NotNull Component getDisplayName() {
                return Component.translatable("item.firefly8.bundler");  // Old Inventory
            }

            @Override
            public @NotNull AbstractContainerMenu createMenu(int pContainerId, @NotNull Inventory pInventory, @NotNull Player pPlayer) {
                IPlayerWithHiddenInventory extPlayer = IPlayerWithHiddenInventory.xdi8$extend(pPlayer);
                //return ChestMenu.sixRows(pContainerId, pInventory, extPlayer.xdi8$getPortalInv());
                return new TakeOnlyChestMenu(pContainerId, pInventory, extPlayer.xdi8$getPortalInv());
            }
        };
    }

    @Override
    public MenuProvider getMenuProvider(@NotNull BlockState pState, @NotNull Level pLevel,
                                        @NotNull BlockPos pPos) {
        return menuProvider();
    }

    private static InteractionHand oppositeHand(InteractionHand hand) {
        return hand == InteractionHand.OFF_HAND ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
    }
}
