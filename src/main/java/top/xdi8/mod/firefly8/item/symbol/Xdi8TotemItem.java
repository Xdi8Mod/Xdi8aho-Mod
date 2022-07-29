package top.xdi8.mod.firefly8.item.symbol;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.xdi8.mod.firefly8.core.totem.TotemAbilities;
import top.xdi8.mod.firefly8.core.totem.TotemAbility;

import java.util.List;
import java.util.Optional;

public class Xdi8TotemItem extends Item {
    public Xdi8TotemItem(Properties pProperties) {
        super(pProperties);
    }

    public static ItemStack withTotemAbility(ItemStack stackIn, TotemAbility ability) {
        ItemStack stack = stackIn.copy();
        final Optional<ResourceLocation> id = TotemAbilities.getId(ability);
        id.ifPresent(resourceLocation ->
                stack.getOrCreateTag().putString("Totem", resourceLocation.toString()));
        return stack;
    }

    @Nullable
    public static TotemAbility getAbility(ItemStack stackIn) {
        final String s = stackIn.getOrCreateTag().getString("Totem");
        if (s.isBlank()) return null;
        return TotemAbilities.byId(new ResourceLocation(s)).orElse(null);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, @NotNull Player pPlayer, @NotNull InteractionHand pUsedHand) {
        final ItemStack item = pPlayer.getItemInHand(pUsedHand);
        final TotemAbility ability = getAbility(item);
        if (ability == null) return InteractionResultHolder.pass(item);
        if (!pLevel.isClientSide()) {
            return ability.activate(pLevel, pPlayer, pUsedHand)
                    .map(InteractionResultHolder::consume)
                    .orElseGet(() -> InteractionResultHolder.pass(item));
        } return InteractionResultHolder.success(item);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        // TODO
    }

    @Override
    public boolean isFoil(@NotNull ItemStack pStack) {
        return super.isFoil(pStack);//TODO
    }
}
