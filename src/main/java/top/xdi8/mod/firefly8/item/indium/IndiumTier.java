package top.xdi8.mod.firefly8.item.indium;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.xdi8.mod.firefly8.item.FireflyItems;

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
        return Tier.super.getTag();  // TODO
    }
}
