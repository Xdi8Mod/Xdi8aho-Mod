package org.featurehouse.mcmod.spm.util.objsettings;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import org.featurehouse.mcmod.spm.blocks.plants.EnchantedSaplings;
import org.featurehouse.mcmod.spm.platform.api.reg.PlatformRegister;

import java.util.function.Supplier;

public final class BlockSettings {
    public static BlockBehaviour.Properties functionalMinable(Material material, float hardness, float blastResistance) {
        return BlockBehaviour.Properties.of(material).destroyTime(hardness).explosionResistance(blastResistance).requiresCorrectToolForDrops();
    }

    public static Supplier<Block> createEnchantedSapling(String id, Supplier<AbstractTreeGrower> saplingGeneratorSupplier) {
        return PlatformRegister.spm().block(id, ()->new EnchantedSaplings(saplingGeneratorSupplier.get(), grassLike()));
    }

    public static BlockBehaviour.Properties grassLike() { return GRASS_LIKE.get(); }

    private static final java.util.function.Supplier<BlockBehaviour.Properties> GRASS_LIKE;

    private BlockSettings() {}

    static {
        GRASS_LIKE = () -> BlockBehaviour.Properties.of(Materials.MATERIAL_PLANT)
                .noCollission()
                .randomTicks()
                .instabreak()
                .sound(SoundType.CROP);
    }

    static boolean canSpawnOnLeaves(BlockState state, BlockGetter world, BlockPos pos, EntityType<?> type) {
        return type == EntityType.OCELOT || type == EntityType.PARROT;
    }

    public static Supplier<Block> createLeaves(String id) {
        return PlatformRegister.spm().block(id,
                ()->new LeavesBlock(BlockBehaviour.Properties.of(Material.LEAVES)
                        .strength(0.2F)
                        .randomTicks()
                        .sound(SoundType.GRASS)
                        .noOcclusion()
                        .isValidSpawn(BlockSettings::canSpawnOnLeaves)
                        .isSuffocating(BlockSettings::alwaysFalse)
                        .isViewBlocking(BlockSettings::alwaysFalse)));
    }

    private static boolean alwaysFalse(BlockState state, BlockGetter world, BlockPos pos) {
        return false;
    }
}