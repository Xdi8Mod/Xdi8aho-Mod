/**
 * custom spawn eggs
 * code source:https://github.com/QWERTY770/MCBBS-Wiki-Mod/blob/1.17/src/main/java/cn/mcbbswiki/qwerty5238/items/technical/CustomSpawnEggItem.java
 */

package top.xdi8.mod.firefly8.item.technical;

import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.common.util.NonNullSupplier;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CustomSpawnEggItem extends SpawnEggItem {
    protected static final List<CustomSpawnEggItem> CUSTOM_EGGS = new ArrayList<>();
    private final Lazy<? extends EntityType<?>> entityTypeSupplier;

    public CustomSpawnEggItem(final NonNullSupplier<? extends EntityType<?>> entityTypeSupplier, int primaryColorIn, int secondaryColorIn, Properties builder) {
        super(null, primaryColorIn, secondaryColorIn, builder);
        this.entityTypeSupplier=Lazy.of(entityTypeSupplier::get);
        CUSTOM_EGGS.add(this);
    }

    public CustomSpawnEggItem(final RegistryObject<? extends EntityType<?>> entityTypeSupplier, int primaryColorIn, int secondaryColorIn, Properties builder) {
        super(null, primaryColorIn, secondaryColorIn, builder);
        this.entityTypeSupplier=Lazy.of(entityTypeSupplier);
        CUSTOM_EGGS.add(this);
    }

    /**
     * Adds all the supplier based spawn eggs to vanilla's map and registers an
     * IDispenseItemBehavior for each of them as normal spawn eggs have one
     * registered for each of them during init.
     * but supplier based ones won't have had their EntityTypes created yet.
     */
    public static void initUnaddedEggs() {
        final Map<EntityType<?>, SpawnEggItem> EGGS = ObfuscationReflectionHelper.getPrivateValue(SpawnEggItem.class, null, "BY_ID");
        // access SpawnEgItem.BY_ID
        DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior() {
            public ItemStack execute(BlockSource source, ItemStack stack) {
                Direction direction = source.getBlockState().getValue(DispenserBlock.FACING);
                EntityType<?> entitytype = ((SpawnEggItem) stack.getItem()).getType(stack.getTag());
                entitytype.spawn(source.getLevel(), stack, null, source.getPos().offset(direction.getNormal()), MobSpawnType.DISPENSER, direction != Direction.UP, false);
                stack.shrink(1);
                return stack;
            }
        };
        for (final SpawnEggItem egg : CUSTOM_EGGS) {
            EGGS.put(egg.getType(null), egg);
            DispenserBlock.registerBehavior(egg, defaultDispenseItemBehavior);
            // ItemColors for each spawn egg don't need to be registered because this method is called before ItemColors is created
        }
        CUSTOM_EGGS.clear();
    }

    @Override
    @Nonnull
    public EntityType<?> getType(@Nullable final CompoundTag tag) {
        return entityTypeSupplier.get();
    }
}
