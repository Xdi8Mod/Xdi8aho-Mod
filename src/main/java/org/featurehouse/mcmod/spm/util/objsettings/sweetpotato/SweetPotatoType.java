package org.featurehouse.mcmod.spm.util.objsettings.sweetpotato;

import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import org.featurehouse.mcmod.spm.SPMMain;

import java.util.Arrays;
import java.util.OptionalDouble;
import java.util.stream.Stream;

public enum SweetPotatoType {
    PURPLE(
            new SweetPotatoComponent(3, 6.0f, 0.35f, OptionalDouble.of(3.0D)),
            new SweetPotatoComponent(8, 9.6f, 0.10f, OptionalDouble.empty()),
            new SweetPotatoComponent(7, 8.6f, 0.60f, OptionalDouble.of(5.0D), true)),
    RED(
            new SweetPotatoComponent(4, 5.0f, 0.30f, OptionalDouble.of(2.6D)),
            new SweetPotatoComponent(7, 9.0f, 0.10f, OptionalDouble.empty()),
            new SweetPotatoComponent(6, 8.0f, 0.55f, OptionalDouble.of(5.0D), true)
    ),
    WHITE(
            new SweetPotatoComponent(2, 4.0f, 0.25f, OptionalDouble.of(2.2D)),
            new SweetPotatoComponent(7, 9.3f, 0.10f, OptionalDouble.empty()),
            new SweetPotatoComponent(6, 8.3f, 0.50f, OptionalDouble.of(5.0D), true)
    );

    private final SweetPotatoComponent raw;
    private final SweetPotatoComponent baked;
    private final SweetPotatoComponent enchanted;

    SweetPotatoType(SweetPotatoComponent raw, SweetPotatoComponent baked, SweetPotatoComponent enchanted) {
        this.raw = raw;
        this.baked = baked;
        this.enchanted = enchanted;
    }

    public SweetPotatoComponent getComponent(SweetPotatoStatus status) {
        return switch (status) {
            case RAW -> raw;
            case BAKED -> baked;
            case ENCHANTED -> enchanted;
            default -> null;
        };
    }

    public ItemLike getRaw() {
        return switch (this) {
            case PURPLE -> SPMMain.PURPLE_POTATO.get();
            case RED -> SPMMain.RED_POTATO.get();
            case WHITE -> SPMMain.WHITE_POTATO.get();
        };
    }

    public ItemLike getBaked() {
        return switch (this) {
            case PURPLE -> SPMMain.BAKED_PURPLE_POTATO.get();
            case RED -> SPMMain.BAKED_RED_POTATO.get();
            case WHITE -> SPMMain.BAKED_WHITE_POTATO.get();
        };
    }

    public Block getCrop() {
        return switch (this) {
            case PURPLE -> SPMMain.PURPLE_POTATO_CROP.get();
            case RED -> SPMMain.RED_POTATO_CROP.get();
            case WHITE -> SPMMain.WHITE_POTATO_CROP.get();
        };
    }

    public ItemLike getEnchanted() {
        return switch (this) {
            case PURPLE -> SPMMain.ENCHANTED_PURPLE_POTATO.get();
            case RED -> SPMMain.ENCHANTED_RED_POTATO.get();
            case WHITE -> SPMMain.ENCHANTED_WHITE_POTATO.get();
        };
    }

    public ItemLike get(SweetPotatoStatus status) {
        return switch (status) {
            case RAW -> this.getRaw();
            case BAKED -> this.getBaked();
            case ENCHANTED -> this.getEnchanted();
            case CROP -> this.getCrop();
        };
    }

    public Stream<SweetPotatoType> getOtherTwo() {
        return Arrays.stream(values()).filter(type -> this != type);
    }
}
