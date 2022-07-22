package top.xdi8.mod.firefly8.world.death;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.level.GameRules;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.xdi8.mod.firefly8.ext.IServerPlayerWithHiddenInventory;

@Mod.EventBusSubscriber(modid = "firefly8")
public class PlayerDeathListener {
    @SubscribeEvent
    public static void onPlayerDeath(LivingDeathEvent event) {
        if (!(event.getEntityLiving() instanceof ServerPlayer oldPlayer))
            return;
        final IServerPlayerWithHiddenInventory ext = IServerPlayerWithHiddenInventory.xdi8$extend(oldPlayer);
        if (!ext.xdi8$moveItemsToPortal()) return;
        event.setCanceled(true);
        //var portalCtx = ext.xdi8$getPortal();
        oldPlayer.setHealth(oldPlayer.getMaxHealth());
        oldPlayer.foodData = new FoodData();
        oldPlayer.removeEntitiesOnShoulder();
        if (oldPlayer.level.getGameRules().getBoolean(GameRules.RULE_FORGIVE_DEAD_PLAYERS)) {
            oldPlayer.tellNeutralMobsThatIDied();
        }
        //final ServerPlayer newPlayer =
                oldPlayer.server.getPlayerList().respawn(oldPlayer, true);
        // TODO: criterion, when dealing with multiplatform
    }
}
