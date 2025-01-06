package top.xdi8.mod.firefly8.core.totem;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.qwerty770.mcmod.xdi8.api.ResourceLocationTool;
import net.minecraft.advancements.critereon.SingleComponentItemPredicate;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import top.xdi8.mod.firefly8.Firefly8;
import top.xdi8.mod.firefly8.item.FireflyDataComponentTypes;

import java.util.ArrayList;
import java.util.List;

public record TotemAbilityPredicate(List<TotemAbility> totemAbilities) implements SingleComponentItemPredicate<String> {
    public static final Codec<TotemAbilityPredicate> CODEC = RecordCodecBuilder.create(
            (instance) -> instance.group(Codec.STRING.listOf().fieldOf("totems").forGetter(TotemAbilityPredicate::getString)).apply(instance, TotemAbilityPredicate::fromString)
    );

    public static TotemAbilityPredicate fromString(List<String> list) {
        return new TotemAbilityPredicate(list.stream().map((string) -> TotemAbilities.byId(ResourceLocationTool.create(string)).orElse(null)).toList());
    }

    public List<String> getString() {
        List<String> list = new ArrayList<>();
        for (TotemAbility ability : totemAbilities) {
            try {
                ResourceLocation location = ability.getId();
                list.add(location.toString());
            } catch (IllegalStateException exception) {
                Firefly8.LOGGER.error(exception.getMessage());
            }
        }
        return list;
    }

    @Override
    public @NotNull DataComponentType<String> componentType() {
        return FireflyDataComponentTypes.TOTEM.get();
    }

    @Override
    public boolean matches(ItemStack stack, String value) {
        return totemAbilities.contains(TotemAbilities.byId(ResourceLocationTool.create(value)).orElse(null));
    }
}
