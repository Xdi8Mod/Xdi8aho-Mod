package top.xdi8.mod.firefly8.core.totem;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;

public interface TotemAbility {
    void activate(Player player, InteractionHand hand);
}
