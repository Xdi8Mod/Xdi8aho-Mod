package top.xdi8.mod.firefly8;

import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.CommandRegistrationEvent;
import dev.architectury.event.events.common.EntityEvent;
import dev.architectury.registry.ReloadListenerRegistry;
import dev.architectury.registry.level.entity.EntityAttributeRegistry;
import net.minecraft.server.packs.PackType;
import org.apache.commons.lang3.mutable.MutableBoolean;
import top.xdi8.mod.firefly8.advancement.AdvancementLoadingContext;
import top.xdi8.mod.firefly8.advancement.criteria.FireflyCriteria;
import top.xdi8.mod.firefly8.block.FireflyBlocks;
import top.xdi8.mod.firefly8.block.cedar.FireflyTreeFeatures;
import top.xdi8.mod.firefly8.block.entity.FireflyBlockEntityTypes;
import top.xdi8.mod.firefly8.block.structure.Xdi8PortalBasicDataLoader;
import top.xdi8.mod.firefly8.command.FireflyCommands;
import top.xdi8.mod.firefly8.core.letters.LettersUtil;
import top.xdi8.mod.firefly8.core.totem.TotemAbilities;
import top.xdi8.mod.firefly8.entity.FireflyEntity;
import top.xdi8.mod.firefly8.entity.FireflyEntityTypes;
import top.xdi8.mod.firefly8.item.FireflyItems;
import top.xdi8.mod.firefly8.item.tint.advanceent.VanillaAdvancements;
import top.xdi8.mod.firefly8.item.tint.brewing.TintedPotionBrewingRecipe;
import top.xdi8.mod.firefly8.particle.FireflyParticles;
import top.xdi8.mod.firefly8.recipe.FireflyRecipes;
import top.xdi8.mod.firefly8.screen.FireflyMenus;
import top.xdi8.mod.firefly8.stats.FireflyStats;
import top.xdi8.mod.firefly8.world.FireflyMobBiomeGen;
import top.xdi8.mod.firefly8.world.Xdi8PoiTypes;
import top.xdi8.mod.firefly8.world.death.PlayerDeathListener;

public class Firefly8 {
    public static String MODID = "firefly8";
    public static void init() {
        activateRegistries();
        // Item
        TintedPotionBrewingRecipe.register();
        // WorldGen
        FireflyMobBiomeGen.registerBiomeModifications();
        AdvancementLoadingContext.EVENT.register(VanillaAdvancements::patchTintedItem);

        ReloadListenerRegistry.register(PackType.SERVER_DATA, new Xdi8PortalBasicDataLoader());
        EntityEvent.LIVING_DEATH.register(((entity, source) -> {
            MutableBoolean mb = new MutableBoolean();
            PlayerDeathListener.onPlayerDeath(entity, mb::setTrue);
            return mb.isTrue() ? EventResult.interruptFalse() : EventResult.pass();
        }));
        EntityAttributeRegistry.register(FireflyEntityTypes.FIREFLY, FireflyEntity::createAttributes);
        CommandRegistrationEvent.EVENT.register(FireflyCommands::init);
    }

    private static void activateRegistries() {
        // Block
        FireflyBlocks.LOG_WRAPPER.run();
        FireflyBlockEntityTypes.LOG_WRAPPER.run();
        Xdi8PoiTypes.LOG_WRAPPER.run();
        // Item
        FireflyItems.LOG_WRAPPER.run();
        // Entity
        FireflyEntityTypes.LOG_WRAPPER.run();
        // Recipe
        FireflyRecipes.LOG_WRAPPER.run();
        // Stats
        FireflyStats.LOG_WRAPPER.run();
        // Particle
        FireflyParticles.LOG_WRAPPER.run();
        // Menu
        FireflyMenus.LOG_WRAPPER.run();
        // Tree Feature
        FireflyTreeFeatures.LOG_WRAPPER.run();
    }

    public static void commonSetup() {
        FireflyItems.registerFuels();
        // Letters
        LettersUtil.fireLetterRegistry();
        // Totem
        TotemAbilities.fireRegistry();
        // Criteria
        FireflyCriteria.init();
    }
}
