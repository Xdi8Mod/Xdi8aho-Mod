package top.xdi8.mod.firefly8.advancement;

import dev.architectury.registry.registries.RegistrySupplier;
import io.github.qwerty770.mcmod.xdi8.registries.InternalRegistryLogWrapper;
import net.minecraft.advancements.critereon.ItemSubPredicate;

import static io.github.qwerty770.mcmod.xdi8.registries.RegistryHelper.criterionTrigger;
import static io.github.qwerty770.mcmod.xdi8.registries.RegistryHelper.itemSubPredicateType;

@SuppressWarnings("unused")
public class FireflyCustomAdvancements {
    public static final InternalRegistryLogWrapper LOG_WRAPPER = InternalRegistryLogWrapper.firefly8("custom_advancements");

    public static final RegistrySupplier<SimpleDieInXdi8ahoTrigger> DIE_IN_XDI8AHO = criterionTrigger("die_in_xdi8aho", SimpleDieInXdi8ahoTrigger::new);
    public static final RegistrySupplier<ItemSubPredicate.Type<TotemAbilityPredicate>> TOTEM_ABILITY_PREDICATE = itemSubPredicateType("totem_ability", TotemAbilityPredicate.CODEC);
}
