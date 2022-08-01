package top.xdi8.mod.firefly8.advancement.criteria;

import org.featurehouse.mcmod.spm.platform.api.advacement.CriterionRegistry;

public class FireflyCriteria {
    public static final GetXdi8TotemTrigger GET_XDI8_TOTEM = CriterionRegistry.register(new GetXdi8TotemTrigger());
    public static final SimpleDieInXdi8ahoTrigger DIE_IN_XDI8AHO = CriterionRegistry.register(new SimpleDieInXdi8ahoTrigger());

    public static void init() {}
}
