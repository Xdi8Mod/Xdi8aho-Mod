package org.featurehouse.mcmod.spm;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.food.Foods;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import org.featurehouse.mcmod.spm.blocks.GrinderBlock;
import org.featurehouse.mcmod.spm.blocks.MagicCubeBlock;
import org.featurehouse.mcmod.spm.blocks.SeedUpdaterBlock;
import org.featurehouse.mcmod.spm.blocks.SweetPotatoesCropBlock;
import org.featurehouse.mcmod.spm.blocks.entities.GrinderBlockEntity;
import org.featurehouse.mcmod.spm.blocks.entities.MagicCubeBlockEntity;
import org.featurehouse.mcmod.spm.blocks.plants.EnchantedBeetrootsBlock;
import org.featurehouse.mcmod.spm.blocks.plants.EnchantedVanillaPotatoesBlock;
import org.featurehouse.mcmod.spm.items.*;
import org.featurehouse.mcmod.spm.platform.api.reg.PlatformRegister;
import org.featurehouse.mcmod.spm.recipe.SeedUpdatingRecipe;
import org.featurehouse.mcmod.spm.screen.GrinderScreenHandler;
import org.featurehouse.mcmod.spm.screen.MagicCubeScreenHandler;
import org.featurehouse.mcmod.spm.screen.SeedUpdaterScreenHandler;
import org.featurehouse.mcmod.spm.util.annotation.StableApi;
import org.featurehouse.mcmod.spm.util.objsettings.BlockSettings;
import org.featurehouse.mcmod.spm.util.objsettings.ItemSettings;
import org.featurehouse.mcmod.spm.util.objsettings.Materials;
import org.featurehouse.mcmod.spm.util.objsettings.sweetpotato.SweetPotatoType;
import org.featurehouse.mcmod.spm.util.registries.AnimalIngredients;
import org.featurehouse.mcmod.spm.util.registries.ComposterHelper;
import org.featurehouse.mcmod.spm.world.gen.tree.EnchantedTreeGen;
import org.featurehouse.mcmod.spm.world.gen.tree.GrassDecorator;
import org.featurehouse.mcmod.spm.world.gen.tree.TreeFeatures2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.function.Supplier;

import static org.featurehouse.mcmod.spm.util.objsettings.BlockSettings.*;
import static org.featurehouse.mcmod.spm.util.registries.RegistryHelper.blockItem;
import static org.featurehouse.mcmod.spm.util.registries.RegistryHelper.defaultItem;

@StableApi
public class SPMMain {
	private SPMMain() {}
	private static final SPMMain INSTANCE = new SPMMain();
	public static SPMMain getInstance() { return INSTANCE; }

	private static final Logger LOGGER = LoggerFactory.getLogger("Sweet Potato Mod");
	public static Logger getLogger() { return LOGGER; }

	public static final String MODID = "sweet_potato";

	// Items
	public static final Supplier<Item> PEEL;

		// Baked Potatoes
	public static final Supplier<Item> BAKED_PURPLE_POTATO;
	public static final Supplier<Item> BAKED_RED_POTATO;
	public static final Supplier<Item> BAKED_WHITE_POTATO;
		// Raw Potatoes
	public static final Supplier<Item> PURPLE_POTATO;
	public static final Supplier<Item> RED_POTATO;
	public static final Supplier<Item> WHITE_POTATO;
		// Enchanted Potatoes
	public static final Supplier<Item> ENCHANTED_PURPLE_POTATO;
	public static final Supplier<Item> ENCHANTED_RED_POTATO;
	public static final Supplier<Item> ENCHANTED_WHITE_POTATO;

		// Misc
	public static final Supplier<Item> POTATO_POWDER;
	public static final Supplier<Item> XMAS_TREATING_BOWL;

	// Blocks
	public static final Supplier<Block> MAGIC_CUBE;
	public static final Supplier<Block> GRINDER;

	public static final Supplier<Block> SEED_UPDATER;

		// Crops
	public static final Supplier<Block> PURPLE_POTATO_CROP;
	public static final Supplier<Block> RED_POTATO_CROP;
	public static final Supplier<Block> WHITE_POTATO_CROP;
	public static final Supplier<Block> ENCHANTED_SAPLING;
	public static final Supplier<Block> ENCHANTED_LEAVES;
	public static final Supplier<Block> ENCHANTED_CROPS;
	public static final Supplier<Block> ENCHANTED_TUBER;

	// Block Items
	public static final Supplier<Item> MAGIC_CUBE_ITEM;
	public static final Supplier<Item> GRINDER_ITEM;
	public static final Supplier<Item> SEED_UPDATER_ITEM;
	public static final Supplier<Item> ENCHANTED_SAPLING_ITEM;
	public static final Supplier<Item> ENCHANTED_LEAVES_ITEM;
	public static final Supplier<Item> ENCHANTED_CROP_SEEDS;
	public static final Supplier<Item> ENCHANTED_CROP_ITEM;
	public static final Supplier<Item> ENCHANTED_TUBER_ITEM;


	// -*- -*- MISC -*- -*- //

	// Screen Handlers
	public static final Supplier<MenuType<SeedUpdaterScreenHandler>> SEED_UPDATER_SCREEN_HANDLER_TYPE;
	public static final Supplier<MenuType<GrinderScreenHandler>> GRINDER_SCREEN_HANDLER_TYPE;
	public static final Supplier<MenuType<MagicCubeScreenHandler>> MAGIC_CUBE_SCREEN_HANDLER_TYPE;

	// Recipe Serializer
	public static final Supplier<RecipeSerializer<SeedUpdatingRecipe>> SEED_UPDATING_RECIPE_SERIALIZER;

	// Recipe Type
	public static final Supplier<RecipeType<SeedUpdatingRecipe>> SEED_UPDATING_RECIPE_TYPE;

	// Block Entities
	public static final Supplier<BlockEntityType<GrinderBlockEntity>> GRINDER_BLOCK_ENTITY_TYPE;
	public static final Supplier<BlockEntityType<MagicCubeBlockEntity>> MAGIC_CUBE_BLOCK_ENTITY_TYPE;

	// Item Tags
	public static final TagKey<Item> RAW_SWEET_POTATOES;
	public static final TagKey<Item> ENCHANTED_SWEET_POTATOES;
	public static final TagKey<Item> ALL_SWEET_POTATOES;

	// Sounds
	public static final Supplier<SoundEvent> AGROFORESTRY_TABLE_FINISH;
	public static final Supplier<SoundEvent> GRINDER_GRIND;
	public static final Supplier<SoundEvent> MAGIC_CUBE_ACTIVATE;
	public static final Supplier<SoundEvent> MAGIC_CUBE_DEACTIVATE;
	public static final Supplier<SoundEvent> MAGIC_CUBE_AMBIENT;

	// Stats
	public static final Supplier<ResourceLocation> INTERACT_WITH_GRINDER;
	public static final Supplier<ResourceLocation> INTERACT_WITH_AGRO;
	public static final Supplier<ResourceLocation> CROP_UPGRADED;
	public static final Supplier<ResourceLocation> SWEET_POTATO_EATEN;
	public static final Supplier<ResourceLocation> INTERACT_WITH_MAGIC_CUBE;

	// Tree Decorator Types
	public static final Supplier<TreeDecoratorType<GrassDecorator>> GRASS_DECORATOR_TYPE;

	//@Override
	public void onInitialize() {
		getLogger().info("Successfully loaded Sweet Potato Mod!");
		ComposterHelper.register();
		//ResourceUtil.loadResource();
		AnimalIngredients.configureParrot();
		TreeFeatures2.activateMe();
	}

	static {
		// Item
		PlatformRegister reg = PlatformRegister.spm();
		PEEL = defaultItem("peel", ItemSettings::misc);
		BAKED_PURPLE_POTATO = reg.item("baked_purple_potato", ()->new BakedSweetPotatoItem(ItemSettings.groupFood(), SweetPotatoType.PURPLE));
		BAKED_RED_POTATO = reg.item("baked_red_potato", ()->new BakedSweetPotatoItem(ItemSettings.groupFood(), SweetPotatoType.RED));
		BAKED_WHITE_POTATO = reg.item("baked_white_potato", ()->new BakedSweetPotatoItem(ItemSettings.groupFood(), SweetPotatoType.WHITE));
		POTATO_POWDER = defaultItem("potato_powder", ItemSettings::misc);
		XMAS_TREATING_BOWL = defaultItem("treating_bowl", ItemSettings::easterEgg);
		ENCHANTED_PURPLE_POTATO = reg.item("enchanted_purple_potato", ()->new EnchantedSweetPotatoItem(ItemSettings.oneFood(), SweetPotatoType.PURPLE));
		ENCHANTED_RED_POTATO = reg.item("enchanted_red_potato", ()->new EnchantedSweetPotatoItem(ItemSettings.oneFood(), SweetPotatoType.RED));
		ENCHANTED_WHITE_POTATO = reg.item("enchanted_white_potato", ()->new EnchantedSweetPotatoItem(ItemSettings.oneFood(), SweetPotatoType.WHITE));

		// Block
		MAGIC_CUBE = reg.block("magic_cube", ()->new MagicCubeBlock(functionalMinable(Materials.MATERIAL_STONE, 10.0F, 1200.0F)));
		GRINDER = reg.block("grinder", ()->new GrinderBlock(functionalMinable(Materials.MATERIAL_STONE, 3.5F, 6.0F)));
		SEED_UPDATER = reg.block("agroforestry_table", ()->new SeedUpdaterBlock(functionalMinable(Materials.MATERIAL_STONE, 3.5F, 6.0F)));
		    // Crops
		PURPLE_POTATO_CROP = reg.block("purple_potatoes", ()->new SweetPotatoesCropBlock(BlockSettings.grassLike(), SweetPotatoType.PURPLE));
		RED_POTATO_CROP = reg.block("red_potatoes", ()->new SweetPotatoesCropBlock(BlockSettings.grassLike(), SweetPotatoType.RED));
		WHITE_POTATO_CROP = reg.block("white_potatoes", ()->new SweetPotatoesCropBlock(BlockSettings.grassLike(), SweetPotatoType.WHITE));
		ENCHANTED_CROPS = reg.block("enchanted_crops", () -> new EnchantedBeetrootsBlock(BlockSettings.grassLike()));
		ENCHANTED_TUBER = reg.block("enchanted_tubers", () -> new EnchantedVanillaPotatoesBlock(BlockSettings.grassLike()));
		// Saplings & Leaves
		ENCHANTED_SAPLING = createEnchantedSapling("enchanted_sapling", EnchantedTreeGen::new);
		ENCHANTED_LEAVES = createLeaves("enchanted_leaves");
		// Block Items
		PURPLE_POTATO = reg.item("purple_potato", ()->new RawSweetPotatoBlockItem(PURPLE_POTATO_CROP.get(), ItemSettings.groupFood(), SweetPotatoType.PURPLE));
		RED_POTATO = reg.item("red_potato", ()->new RawSweetPotatoBlockItem(RED_POTATO_CROP.get(), ItemSettings.groupFood(), SweetPotatoType.RED));
		WHITE_POTATO = reg.item("white_potato", ()->new RawSweetPotatoBlockItem(WHITE_POTATO_CROP.get(), ItemSettings.groupFood(), SweetPotatoType.WHITE));

		ENCHANTED_SAPLING_ITEM = EnchantedBlockItem.of("enchanted_sapling", ENCHANTED_SAPLING, ItemSettings::uncommonDecorations);
		ENCHANTED_LEAVES_ITEM = EnchantedBlockItem.of("enchanted_leaves", ENCHANTED_LEAVES, ItemSettings::uncommonDecorations);
		ENCHANTED_CROP_SEEDS = AliasedEnchantedItem.of("enchanted_crop_seeds", ENCHANTED_CROPS);
		ENCHANTED_TUBER_ITEM = AliasedEnchantedItem.ofMiscFood("enchanted_tuber", ENCHANTED_TUBER, Foods.COOKED_CHICKEN);
		ENCHANTED_CROP_ITEM = defaultItem("enchanted_crop", () -> ItemSettings.groupFood().rarity(Rarity.UNCOMMON).food(Foods.BREAD));

			// Functional Blocks' Items
		MAGIC_CUBE_ITEM = blockItem("magic_cube", MAGIC_CUBE, ItemSettings::decorations);
		GRINDER_ITEM = blockItem("grinder", GRINDER, ItemSettings::decorations);
		SEED_UPDATER_ITEM = blockItem("agroforestry_table", SEED_UPDATER, ItemSettings::decorations);
		//ENCHANTED_OAK_SAPLING_ITEM = EnchantedBlockItem.of("enchanted_oak_sapling", ENCHANTED_OAK_SAPLING, ItemSettings::uncommonDecorations);

		// Screen Handler
		SEED_UPDATER_SCREEN_HANDLER_TYPE = reg.menu("seed_updater", SeedUpdaterScreenHandler::new);
		GRINDER_SCREEN_HANDLER_TYPE = reg.menu("grinder", GrinderScreenHandler::new);
		MAGIC_CUBE_SCREEN_HANDLER_TYPE = reg.menu("magic_cube", MagicCubeScreenHandler::new);

		// Recipe Serializer
		SEED_UPDATING_RECIPE_SERIALIZER = reg.recipeSerializer("seed_updating", SeedUpdatingRecipe.Serializer::new);

		// Recipe Type
		SEED_UPDATING_RECIPE_TYPE = reg.recipeType("seed_updating");

		// Block Entity
		GRINDER_BLOCK_ENTITY_TYPE = reg.blockEntity("grinder", GrinderBlockEntity::new, Collections.singleton(GRINDER));
		MAGIC_CUBE_BLOCK_ENTITY_TYPE = reg.blockEntity("magic_cube", MagicCubeBlockEntity::new, Collections.singleton(MAGIC_CUBE));

		// Item Tags
		RAW_SWEET_POTATOES = reg.itemTag("raw_sweet_potatoes");
		ENCHANTED_SWEET_POTATOES = reg.itemTag("enchanted_sweet_potatoes");
		ALL_SWEET_POTATOES = reg.itemTag("sweet_potatoes");

		// Sounds
		AGROFORESTRY_TABLE_FINISH = reg.sound("block.agroforestry_table.finish");
		GRINDER_GRIND = reg.sound("block.grinder.grind");
		MAGIC_CUBE_ACTIVATE = reg.sound("block.magic_cube.activate");
		MAGIC_CUBE_DEACTIVATE = reg.sound("block.magic_cube.deactivate");
		MAGIC_CUBE_AMBIENT = reg.sound("block.magic_cube.ambient");

		// Stats
		INTERACT_WITH_GRINDER = reg.customStat("interact_with_grinder");
		INTERACT_WITH_AGRO = reg.customStat("interact_with_agroforestry_table");
		CROP_UPGRADED = reg.customStat("crop_upgraded");
		SWEET_POTATO_EATEN = reg.customStat("sweet_potato_eaten");
		INTERACT_WITH_MAGIC_CUBE = reg.customStat("interact_with_magic_cube");

		// Tree Decorator Types
		GRASS_DECORATOR_TYPE = reg.treeDecoratorType("grass", () -> GrassDecorator.CODEC);
	}
}
