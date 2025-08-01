package top.xdi8.mod.firefly8;

import dev.architectury.event.events.common.CommandRegistrationEvent;
import dev.architectury.registry.ReloadListenerRegistry;
import dev.architectury.registry.level.entity.EntityAttributeRegistry;
import io.github.qwerty770.mcmod.xdi8.registries.RegistryHelper;
import io.github.qwerty770.mcmod.xdi8.registries.ResourceLocationTool;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.level.block.ComposterBlock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.xdi8.mod.firefly8.advancement.FireflyCustomAdvancements;
import top.xdi8.mod.firefly8.block.FireflyBlocks;
import top.xdi8.mod.firefly8.block.entity.FireflyBlockEntityTypes;
import top.xdi8.mod.firefly8.block.structure.Xdi8PortalBasicDataLoader;
import top.xdi8.mod.firefly8.command.FireflyCommands;
import top.xdi8.mod.firefly8.core.letters.LettersUtil;
import top.xdi8.mod.firefly8.core.totem.TotemAbilities;
import top.xdi8.mod.firefly8.entity.FireflyEntity;
import top.xdi8.mod.firefly8.entity.FireflyEntityTypes;
import top.xdi8.mod.firefly8.item.FireflyDataComponentTypes;
import top.xdi8.mod.firefly8.item.FireflyItemTags;
import top.xdi8.mod.firefly8.item.FireflyItems;
import top.xdi8.mod.firefly8.particle.FireflyParticles;
import top.xdi8.mod.firefly8.recipe.FireflyRecipes;
import top.xdi8.mod.firefly8.screen.FireflyMenus;
import top.xdi8.mod.firefly8.stats.FireflyStats;
import top.xdi8.mod.firefly8.world.FireflyMobBiomeGen;
import top.xdi8.mod.firefly8.world.FireflyPoiTypes;
import top.xdi8.mod.firefly8.world.FireflyTreeFeatures;

public class Firefly8 {
    public static final String MODID = "firefly8";
    public static final Logger LOGGER = LoggerFactory.getLogger("Firefly8");

    public static void init() {
        CommandRegistrationEvent.EVENT.register((dispatcher, registry, selection) -> FireflyCommands.init(dispatcher, selection));
        ReloadListenerRegistry.register(PackType.SERVER_DATA, new Xdi8PortalBasicDataLoader(), ResourceLocationTool.create("firefly8:xdi8_portal_data"));
        EntityAttributeRegistry.register(FireflyEntityTypes.FIREFLY, FireflyEntity::createAttributes);
        FireflyMobBiomeGen.registerBiomeModifications();
    }

    public static void activateRegistries() {
        // Block
        FireflyBlocks.LOG_WRAPPER.run();
        FireflyBlockEntityTypes.LOG_WRAPPER.run();
        FireflyPoiTypes.LOG_WRAPPER.run();
        // Item
        FireflyItems.LOG_WRAPPER.run();
        FireflyItemTags.LOG_WRAPPER.run();
        FireflyDataComponentTypes.LOG_WRAPPER.run();
        // Recipe
        FireflyRecipes.LOG_WRAPPER.run();
        // Menu
        FireflyMenus.LOG_WRAPPER.run();
        // Particle
        FireflyParticles.LOG_WRAPPER.run();
        // Entity
        FireflyEntityTypes.LOG_WRAPPER.run();
        // Stats
        FireflyStats.LOG_WRAPPER.run();
        // Criteria
        FireflyCustomAdvancements.LOG_WRAPPER.run();
        // Tree Feature
        FireflyTreeFeatures.LOG_WRAPPER.run();
        // All registries
        RegistryHelper.registerAll();
    }

    public static void commonSetup() {
        FireflyItems.registerFuels();
        Firefly8.registerCompostableItems();
        // Letters
        LettersUtil.fireLetterRegistry();
        // Totem
        TotemAbilities.fireRegistry();
    }

    private static void registerCompostableItems() {
        ComposterBlock.COMPOSTABLES.put(FireflyItems.CEDAR_LEAVES.get(), 0.3f);
        ComposterBlock.COMPOSTABLES.put(FireflyItems.CEDAR_SAPLING.get(), 0.3f);
    }
}
