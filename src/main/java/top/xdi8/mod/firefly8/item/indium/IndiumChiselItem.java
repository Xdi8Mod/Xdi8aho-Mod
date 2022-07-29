package top.xdi8.mod.firefly8.item.indium;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.jetbrains.annotations.NotNull;
import top.xdi8.mod.firefly8.block.symbol.SymbolStoneBlock;
import top.xdi8.mod.firefly8.core.letters.KeyedLetter;
import top.xdi8.mod.firefly8.screen.ChiselMenu;
import top.xdi8.mod.firefly8.stats.FireflyStats;

import java.util.Optional;

public class IndiumChiselItem extends Item {
    public IndiumChiselItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext pContext) {
        final BlockPos clickedPos = pContext.getClickedPos();
        final Level level = pContext.getLevel();
        if (level.getBlockState(clickedPos).is(SymbolStoneBlock.fromLetter(KeyedLetter.empty()))) {
            MutableBoolean b = new MutableBoolean(false);
            Optional.ofNullable(pContext.getPlayer()).ifPresent(p -> {
                //if (!p.isCrouching()) return;
                p.openMenu(new MenuProvider() {
                    @Override
                    public @NotNull Component getDisplayName() {
                        return new TranslatableComponent("block.firefly8.symbol_stone.chiseling");
                    }

                    @Override
                    public @NotNull ChiselMenu createMenu(int pContainerId, @NotNull Inventory pInventory, @NotNull Player pPlayer) {
                        return new ChiselMenu(pContainerId, level, clickedPos,
                                ContainerLevelAccess.create(level, clickedPos),
                                pInventory, () -> {
                            // damage
                            pContext.getItemInHand().hurtAndBreak(1, pPlayer, p2 ->
                                    p2.broadcastBreakEvent(pContext.getHand()));
                        });
                    }
                });
                p.awardStat(FireflyStats.INTERACT_WITH_CHISEL.get());
                b.setTrue();
            });
            if (b.isTrue()) return InteractionResult.CONSUME;
        }
        return InteractionResult.PASS;
    }
}
