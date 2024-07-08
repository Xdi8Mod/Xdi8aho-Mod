package top.xdi8.mod.firefly8.core.letters;

import net.minecraft.resources.ResourceLocation;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum DefaultXdi8Letters implements KeyedLetter {
    LETTER_b("b", 990049),
    LETTER_p("p", 990050),
    LETTER_m("m", 990051),
    LETTER_w("w", 990052),
    LETTER_j("j", 990053),
    LETTER_q("q", 990054),
    LETTER_x("x", 990055),
    LETTER_y("y", 990056),
    LETTER_n("n", 990057),
    LETTER_z("z", 990058),
    LETTER_dx("dx", 990059),
    LETTER_s("s", 990060),
    LETTER_r("r", 990061),
    LETTER_hx("hx", 990062),
    LETTER_nx("nx", 990063),
    LETTER_l("l", 990064),
    LETTER_d("d", 990065),
    LETTER_t("t", 990066),
    LETTER_g("g", 990067),
    LETTER_k("k", 990068),
    LETTER_h("h", 990069),
    LETTER_4("4", 990070),
    LETTER_5("5", 990071),
    LETTER_v("v", 990072),
    LETTER_fx("fx", 990073),
    LETTER_7("7", 990074),
    LETTER_bx("bx", 990075),
    LETTER_c("c", 990076),
    LETTER_f("f", 990077),
    LETTER_u("u", 990078),
    LETTER_a("a", 990079),
    LETTER_o("o", 990080),
    LETTER_e("e", 990081),
    LETTER_ex("ex", 990082),
    LETTER_ax("ax", 990083),
    LETTER_yx("yx", 990084),
    LETTER_lx("lx", 990085),
    LETTER_6("6", 990086),
    LETTER_2("2", 990087),
    LETTER_tx("tx", 990088),
    LETTER_8("8", 990089),
    LETTER_3("3", 990090),
    LETTER_vx("vx", 990091),
    LETTER_1("1", 990092),
    LETTER_i("i", 990093),
    ;
    private final int lowercase;
    private final int middleCase;
    private final int uppercase;
    private final ResourceLocation resourceLocation;

    DefaultXdi8Letters(String id, int lowercase) {
        this.lowercase = lowercase;
        this.uppercase = lowercase - 0x60;
        this.middleCase = lowercase + 0x60;
        this.resourceLocation = new ResourceLocation("firefly8", id);
    }

    static final Map<ResourceLocation, KeyedLetter> BY_ID =
            Arrays.stream(values()).collect(Collectors.toMap(
                    DefaultXdi8Letters::id, Function.identity()));

    @Override
    public ResourceLocation id() {
        return resourceLocation;
    }

    @Override
    public boolean hasLowercase() {
        return true;
    }

    @Override
    public int lowercase() {
        return this.lowercase;
    }

    @Override
    public boolean hasMiddleCase() {
        return true;
    }

    @Override
    public int middleCase() {
        return this.middleCase;
    }

    @Override
    public boolean hasUppercase() {
        return true;
    }

    @Override
    public int uppercase() {
        return this.uppercase;
    }

    @Override
    public String toString() {
        return Character.toString(this.uppercase());
    }
}
