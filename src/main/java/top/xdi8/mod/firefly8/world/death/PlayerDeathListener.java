package top.xdi8.mod.firefly8.world.death;

import com.google.common.base.Suppliers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.level.GameRules;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.xdi8.mod.firefly8.ext.IServerPlayerWithHiddenInventory;
import top.xdi8.mod.firefly8.network.FireflyNetwork;
import top.xdi8.mod.firefly8.world.Xdi8DimensionUtils;

@Mod.EventBusSubscriber(modid = "firefly8")
public class PlayerDeathListener {
    @SubscribeEvent
    public static void onPlayerDeath(LivingDeathEvent event) {
        onPlayerDeath(event.getEntityLiving(), () -> event.setCanceled(true));
    }

    public static void onPlayerDeath(LivingEntity livingEntity, Runnable cancelEngine) {
        if (!(livingEntity instanceof Player player))
            return;
        if (!Xdi8DimensionUtils.canRedirectRespawn(player.getLevel())) return;
        if (!(player instanceof ServerPlayer oldPlayer)) {
            cancelEngine.run();
            return;
        }
        oldPlayer.unRide();
        oldPlayer.removeEntitiesOnShoulder();
        oldPlayer.setHealth(oldPlayer.getMaxHealth());
        oldPlayer.foodData = new FoodData();
        final IServerPlayerWithHiddenInventory ext = IServerPlayerWithHiddenInventory.xdi8$extend(oldPlayer);
        if (!ext.xdi8$moveItemsToPortal()) {
            FireflyNetwork.CHANNEL.sendS2CPlayer(FireflyNetwork.S2C_DIE_INDEED, Suppliers.ofInstance(oldPlayer));
            return;
        }
        cancelEngine.run();
        if (oldPlayer.level.getGameRules().getBoolean(GameRules.RULE_FORGIVE_DEAD_PLAYERS)) {
            oldPlayer.tellNeutralMobsThatIDied();
        }
        oldPlayer.getLevel().removePlayerImmediately(oldPlayer, Entity.RemovalReason.CHANGED_DIMENSION);
        FireflyNetwork.CHANNEL.sendS2CPlayer(FireflyNetwork.S2C_PREPARE_RESPAWN, Suppliers.ofInstance(oldPlayer));
    }
}
