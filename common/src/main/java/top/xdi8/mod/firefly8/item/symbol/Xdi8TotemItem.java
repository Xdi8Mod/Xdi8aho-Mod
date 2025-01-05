package top.xdi8.mod.firefly8.item.symbol;

import io.github.qwerty770.mcmod.xdi8.api.ResourceLocationTool;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.xdi8.mod.firefly8.core.totem.TotemAbilities;
import top.xdi8.mod.firefly8.core.totem.TotemAbility;
import top.xdi8.mod.firefly8.item.FireflyDataComponentTypes;

import java.util.List;

public class Xdi8TotemItem extends Item {
    public Xdi8TotemItem(Properties pProperties) {
        super(pProperties);
    }

    public static ItemStack withTotemAbility(ItemStack stack, TotemAbility ability) {
        TotemAbilities.getId(ability).ifPresent(location -> stack.applyComponents(
                DataComponentMap.builder().set(FireflyDataComponentTypes.TOTEM.get(), location.toString()).build()));
        return stack;
    }

    @Nullable
    public static TotemAbility getAbility(ItemStack stack) {
        final String s = stack.get(FireflyDataComponentTypes.TOTEM.get());
        if (s == null || s.isBlank()) return null;
        return TotemAbilities.byId(ResourceLocationTool.create(s)).orElse(null);
    }

    @Override
    public @NotNull InteractionResult use(@NotNull Level pLevel, @NotNull Player pPlayer, @NotNull InteractionHand pUsedHand) {
        final ItemStack item = pPlayer.getItemInHand(pUsedHand);
        final TotemAbility ability = getAbility(item);
        if (ability == null) return InteractionResult.PASS;
        if (!pLevel.isClientSide()) {
            return ability.activate(pLevel, pPlayer, pUsedHand).isPresent() ? InteractionResult.CONSUME : InteractionResult.PASS;
        }
        return InteractionResult.SUCCESS.heldItemTransformedTo(item);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull Item.TooltipContext context, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
        @Nullable TotemAbility ability = getAbility(stack);
        if (ability != null) {
            MutableComponent component = Component.translatable("xdi8.totem.attribute");
            component = component.append(Component.translatable("xdi8.totem.attribute." + ability.getId()));
            tooltipComponents.add(component);
        }
    }

    @Override
    public boolean isFoil(@NotNull ItemStack pStack) {
        return getAbility(pStack) != null || super.isFoil(pStack);
    }
}
