package top.xdi8.mod.firefly8.item.indium;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.MinecraftForge;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.xdi8.mod.firefly8.item.FireflyItems;
import top.xdi8.mod.firefly8.item.indium.event.DropIndiumNuggetEvent;
import top.xdi8.mod.firefly8.stats.FireflyStats;

public class IndiumTier implements Tier {
    @Override
    public int getUses() {
        return 30;  // only a placeholder
    }

    @Override
    public float getSpeed() {
        return 4.0F;
    }

    @Override
    public float getAttackDamageBonus() {
        return 1.5F;
    }

    @Override
    public int getLevel() {
        return 1;
    }

    @Override
    public int getEnchantmentValue() {
        return 8;
    }

    @Override
    public @NotNull Ingredient getRepairIngredient() {
        return Ingredient.of(FireflyItems.INDIUM_INGOT.get());
    }

    @Nullable
    @Override
    public TagKey<Block> getTag() {
        // Keep null, because there is nothing special to dig with indium tools right now.
        return null;
    }

    static void dropNuggets(@NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull BlockState pState,
                            @NotNull BlockPos pPos, @NotNull LivingEntity pEntityLiving) {
        if (pEntityLiving.getLevel().isClientSide()) return;
        if (pEntityLiving instanceof Player player) {
            final DropIndiumNuggetEvent.Mine event = new DropIndiumNuggetEvent.Mine(pStack, pLevel, pState, pPos, player);
            if (!MinecraftForge.EVENT_BUS.post(event))
                dropNuggetsImpl(player, event.getChance());
        }
    }

    static void dropNuggets(@NotNull ItemStack pStack, @NotNull LivingEntity pTarget, @NotNull LivingEntity pAttacker) {
        if (pAttacker.getLevel().isClientSide()) return;
        if (pAttacker instanceof Player player) {
            final DropIndiumNuggetEvent.Attack event = new DropIndiumNuggetEvent.Attack(pStack, pTarget, player);
            if (!MinecraftForge.EVENT_BUS.post(event))
                dropNuggetsImpl(player, event.getChance());
        }
    }

    private static void dropNuggetsImpl(Player player, float chance) {
        if (player.getRandom().nextFloat() < chance) {
            ItemStack nugget = new ItemStack(FireflyItems.INDIUM_NUGGET.get());
            if (!player.getInventory().add(nugget)) {
                player.drop(nugget, true);
            }
            player.awardStat(FireflyStats.INDIUM_NUGGETS_DROPPED.get());
        }
    }
}
