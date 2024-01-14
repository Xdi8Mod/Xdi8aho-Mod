package top.xdi8.mod.firefly8.item.indium;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.xdi8.mod.firefly8.item.FireflyItems;
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
        return 14;
    }

    @Override
    public @NotNull Ingredient getRepairIngredient() {
        return Ingredient.of(FireflyItems.INDIUM_INGOT.get());
    }

    public static final ResourceLocation LOOT_NUGGETS_ATTACK = new ResourceLocation("firefly8", "misc/indium_nuggets_attack");
    public static final ResourceLocation LOOT_NUGGETS_MINE = new ResourceLocation("firefly8", "misc/indium_nuggets_mine");

    private static void dropNuggets(ServerLevel level, Player player,
                                    ItemStack tool, @Nullable BlockState blockState,
                                    Vec3 vec3,
                                    @Nullable LivingEntity target,
                                    ResourceLocation lootTable) {
        var table = level.getServer().getLootTables().get(lootTable);
        LootContext.Builder builder = new LootContext.Builder(level)
                .withLuck(player.getLuck())
                .withRandom(player.getRandom())
                .withParameter(LootContextParams.ORIGIN, vec3)
                .withParameter(LootContextParams.TOOL, tool)
                .withParameter(LootContextParams.THIS_ENTITY, player);
        if (blockState != null) builder.withParameter(LootContextParams.BLOCK_STATE, blockState);
        if (target != null) {
            builder.withParameter(LootContextParams.THIS_ENTITY, target)
                    .withParameter(LootContextParams.KILLER_ENTITY, player);
        }
        table.getRandomItems(builder.create(LOOT_PARAMS), stack -> {
            player.drop(stack, true);
            player.awardStat(FireflyStats.INDIUM_NUGGETS_DROPPED.get());
        });
    }

    private static final LootContextParamSet LOOT_PARAMS = LootContextParamSet.builder()
            .required(LootContextParams.ORIGIN)
            .required(LootContextParams.TOOL)
            .required(LootContextParams.THIS_ENTITY)
            .optional(LootContextParams.KILLER_ENTITY)
            .optional(LootContextParams.BLOCK_STATE).build();

    static void dropNuggets(@NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull BlockState pState,
                            @NotNull BlockPos pPos, @NotNull LivingEntity pEntityLiving) {
        if (pEntityLiving.getLevel().isClientSide()) return;
        if (pEntityLiving instanceof Player player) {
            dropNuggets(((ServerLevel) pLevel), player, pStack, pState, Vec3.atCenterOf(pPos), null, LOOT_NUGGETS_MINE);
        }
    }

    static void dropNuggets(@NotNull ItemStack pStack, @NotNull LivingEntity pTarget, @NotNull LivingEntity pAttacker) {
        if (pAttacker.getLevel().isClientSide()) return;
        if (pAttacker instanceof Player player) {
            dropNuggets(((ServerLevel) pAttacker.getLevel()), player, pStack, null, player.position(), pTarget, LOOT_NUGGETS_ATTACK);
        }
    }

}
