package top.xdi8.mod.firefly8.item.indium.event;

import com.mojang.logging.annotations.FieldsAreNonnullByDefault;
import com.mojang.logging.annotations.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@FieldsAreNonnullByDefault
@ParametersAreNonnullByDefault
@SuppressWarnings("unused")

@Cancelable
public abstract sealed class DropIndiumNuggetEvent extends Event {
    private final Player player;
    private final ItemStack tool;

    private float chance = 0.45f;

    DropIndiumNuggetEvent(Player player, ItemStack tool) {
        this.player = player;
        this.tool = tool;
    }

    public static final class Attack extends DropIndiumNuggetEvent {
        private final LivingEntity target;

        public Attack(ItemStack stack, LivingEntity target, Player attacker) {
            super(attacker, stack);
            this.target = target;
        }

        public LivingEntity getTarget() {
            return target;
        }
    }

    public static final class Mine extends DropIndiumNuggetEvent {
        private final Level level;
        private final BlockPos blockPos;
        private final BlockState blockState;

        public Mine(ItemStack tool, Level level, BlockState blockState, BlockPos blockPos, Player player) {
            super(player, tool);
            this.level = level;
            this.blockPos = blockPos;
            this.blockState = blockState;
        }

        public Level getLevel() {
            return level;
        }

        public BlockPos getBlockPos() {
            return blockPos;
        }

        public BlockState getBlockState() {
            return blockState;
        }
    }

    public Player getPlayer() {
        return player;
    }

    public ItemStack getTool() {
        return tool;
    }

    public float getChance() {
        return chance;
    }

    public void setChance(float chance) {
        this.chance = chance;
    }
}
