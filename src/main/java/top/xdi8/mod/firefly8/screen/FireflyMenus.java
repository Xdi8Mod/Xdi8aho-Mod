package top.xdi8.mod.firefly8.screen;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FireflyMenus {
    public static final DeferredRegister<MenuType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.CONTAINERS, "firefly8");

    public static final RegistryObject<MenuType<TakeOnlyChestMenu>> TAKE_ONLY_CHEST =
            create("take_only_chest", TakeOnlyChestMenu::new);
    public static final RegistryObject<MenuType<ChiselMenu>> CHISEL =
            create("chisel", ChiselMenu::new);
    public static final RegistryObject<MenuType<Xdi8TableMenu>> XDI8_TABLE =
            create("xdi8_table", Xdi8TableMenu::new);

    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> create(String id, MenuType.MenuSupplier<T> sup) {
        return REGISTRY.register(id, () -> new MenuType<>(sup));
    }
}
