package io.github.qwerty770.mcmod.spmreborn.util.registries;

import dev.architectury.registry.registries.RegistrySupplier;
import io.github.qwerty770.mcmod.spmreborn.util.annotation.StableApi;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

// Added on 2023/11/26 after deleting io.github.qwerty770.mcmod.spmreborn.util.objsettings.BlockSettings
// Update to Minecraft 1.21.3 -- 2024/11/22  Update block properties
@StableApi
public class BlockUtils {
    public static RegistrySupplier<Block> createPotted(String id, RegistrySupplier<Block> inside) {
        return RegistryHelper.block(id, (properties) -> new FlowerPotBlock(inside.get(), properties),
                BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY));
    }

    public static RegistrySupplier<Block> createLeaves(String id) {
        return RegistryHelper.block(id, LeavesBlock::new,
                BlockBehaviour.Properties.of()
                        .mapColor(MapColor.PLANT)
                        .strength(0.2f)
                        .randomTicks()
                        .sound(SoundType.GRASS)
                        .noOcclusion()
                        .isValidSpawn(BlockUtils::ocelotOrParrot)
                        .isSuffocating(BlockUtils::never)
                        .isViewBlocking(BlockUtils::never)
                        .ignitedByLava()
                        .pushReaction(PushReaction.DESTROY)
                        .isRedstoneConductor(BlockUtils::never));
    }

    public static final BlockBehaviour.Properties crop = BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY);
    public static final BlockBehaviour.Properties grass = BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY);

    public static BlockBehaviour.Properties createFunctionalBlock(float hardness, float blastResistance) {
        return BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS).sound(SoundType.WOOD).destroyTime(hardness).explosionResistance(blastResistance).requiresCorrectToolForDrops();
    }

    // private methods from net.minecraft.world.level.block.Blocks
    private static Boolean ocelotOrParrot(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, EntityType<?> entityType) {
        return entityType == EntityType.OCELOT || entityType == EntityType.PARROT;
    }
    private static Boolean never(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return false;
    }
}
