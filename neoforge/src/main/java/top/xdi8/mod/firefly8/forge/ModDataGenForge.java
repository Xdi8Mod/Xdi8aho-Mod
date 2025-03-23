package top.xdi8.mod.firefly8.forge;

import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import top.xdi8.mod.firefly8.forge.datagen.*;

import java.util.List;
import java.util.Set;

@EventBusSubscriber(modid = "firefly8", bus = EventBusSubscriber.Bus.MOD)
public class ModDataGenForge {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Client event) {
        event.createProvider((output, lookupProvider) -> new LootTableProvider(
                output, Set.of(),
                List.of(new LootTableProvider.SubProviderEntry(
                        ModLootTableProvider::new,
                        LootContextParamSets.BLOCK // it makes sense to use BLOCK here
                )), lookupProvider));
        event.createProvider(ModModelProvider::new);
        event.createProvider(ModRecipeProvider.Runner::new);
    }
}
