package top.xdi8.mod.firefly8.item.indium;

import io.github.qwerty770.mcmod.xdi8.registries.ResourceLocationTool;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.context.ContextKeySet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.xdi8.mod.firefly8.item.FireflyItemTags;
import top.xdi8.mod.firefly8.stats.FireflyStats;

public class IndiumToolMaterial {
    public static final ToolMaterial INDIUM = new ToolMaterial(BlockTags.INCORRECT_FOR_STONE_TOOL, 30, 4.0F, 1.5F, 14, FireflyItemTags.INDIUM_TOOL_MATERIALS.tagKey());
    public static final ResourceKey<LootTable> LOOT_NUGGETS_ATTACK = createKey("misc/indium_nuggets_attack");
    public static final ResourceKey<LootTable> LOOT_NUGGETS_MINE = createKey("misc/indium_nuggets_mine");

    private static ResourceKey<LootTable> createKey(String id){
        return ResourceKey.create(Registries.LOOT_TABLE, ResourceLocationTool.create("firefly8", id));
    }

    private static void dropNuggets(ServerLevel level, Player player, ItemStack tool, @Nullable BlockState blockState, Vec3 vec3, @Nullable LivingEntity target, ResourceKey<LootTable> lootTable) {
        var table = level.getServer().reloadableRegistries().getLootTable(lootTable);
        LootParams.Builder builder = new LootParams.Builder(level)
                .withLuck(player.getLuck())
                .withParameter(LootContextParams.ORIGIN, vec3)
                .withParameter(LootContextParams.TOOL, tool)
                .withParameter(LootContextParams.THIS_ENTITY, player);
        if (blockState != null) builder.withParameter(LootContextParams.BLOCK_STATE, blockState);
        if (target != null) {
            builder.withParameter(LootContextParams.THIS_ENTITY, target)
                    .withParameter(LootContextParams.ATTACKING_ENTITY, player);
        }
        table.getRandomItems(builder.create(LOOT_PARAMS), stack -> {
            player.drop(stack, true);
            player.awardStat(FireflyStats.INDIUM_NUGGETS_DROPPED.get());
        });
    }

    private static final ContextKeySet LOOT_PARAMS = new ContextKeySet.Builder()
            .required(LootContextParams.ORIGIN)
            .required(LootContextParams.TOOL)
            .required(LootContextParams.THIS_ENTITY)
            .optional(LootContextParams.ATTACKING_ENTITY)
            .optional(LootContextParams.BLOCK_STATE).build();

    static void dropNuggets(@NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull BlockState pState,
                            @NotNull BlockPos pPos, @NotNull LivingEntity pEntityLiving) {
        if (pLevel.isClientSide() || pState.getDestroySpeed(pLevel, pPos) == 0.0f) return;
        if (pEntityLiving instanceof Player player) {
            dropNuggets(((ServerLevel) pLevel), player, pStack, pState, Vec3.atCenterOf(pPos), null, LOOT_NUGGETS_MINE);
        }
    }

    static void dropNuggets(@NotNull ItemStack pStack, @NotNull LivingEntity pTarget, @NotNull LivingEntity pAttacker) {
        if (pAttacker.level().isClientSide()) return;
        if (pAttacker instanceof Player player) {
            dropNuggets(((ServerLevel) pAttacker.level()), player, pStack, null, player.position(), pTarget, LOOT_NUGGETS_ATTACK);
        }
    }
}
