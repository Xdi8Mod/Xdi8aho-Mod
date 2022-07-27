package top.xdi8.mod.firefly8.world.death;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.level.GameRules;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;
import top.xdi8.mod.firefly8.ext.IServerPlayerWithHiddenInventory;
import top.xdi8.mod.firefly8.network.FireflyNetwork;
import top.xdi8.mod.firefly8.world.Xdi8DimensionUtils;

@Mod.EventBusSubscriber(modid = "firefly8")
public class PlayerDeathListener {
    @SubscribeEvent
    public static void onPlayerDeath(LivingDeathEvent event) {
        if (!(event.getEntityLiving() instanceof Player player))
            return;
        if (!Xdi8DimensionUtils.canRedirectRespawn(player.getLevel())) return;
        if (!(player instanceof ServerPlayer oldPlayer)) {
            event.setCanceled(true);
            return;
        }
        oldPlayer.unRide();
        oldPlayer.removeEntitiesOnShoulder();
        final IServerPlayerWithHiddenInventory ext = IServerPlayerWithHiddenInventory.xdi8$extend(oldPlayer);
        if (!ext.xdi8$moveItemsToPortal()) {
            FireflyNetwork.CHANNEL.send(PacketDistributor.PLAYER.with(() -> oldPlayer), FireflyNetwork.S2CDieIndeed.getInstance());
            return;
        }
        event.setCanceled(true);
        oldPlayer.setHealth(oldPlayer.getMaxHealth());
        oldPlayer.foodData = new FoodData();
        if (oldPlayer.level.getGameRules().getBoolean(GameRules.RULE_FORGIVE_DEAD_PLAYERS)) {
            oldPlayer.tellNeutralMobsThatIDied();
        }
        oldPlayer.getLevel().removePlayerImmediately(oldPlayer, Entity.RemovalReason.CHANGED_DIMENSION);
        // TODO: criterion, when dealing with multiplatform
    }
}
