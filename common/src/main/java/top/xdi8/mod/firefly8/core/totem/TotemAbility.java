package top.xdi8.mod.firefly8.core.totem;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Optional;

@FunctionalInterface
public interface TotemAbility {
    /**
     * Empty: pass this event <br>
     * Present: return item to inventory
     */
    Optional<ItemStack> activate(Level level, Player player, InteractionHand hand);

    default ResourceLocation getId() {
        return TotemAbilities.getId(this).orElseThrow(() -> new IllegalStateException("Trying to get id from unregistered TotemAbility"));
    }
}
