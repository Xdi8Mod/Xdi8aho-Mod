package top.xdi8.mod.firefly8.screen;

import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FireflyMenus {
    public static final DeferredRegister<MenuType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.CONTAINERS, "firefly8");

    public static final RegistryObject<MenuType<TakeOnlyChestMenu>> TAKE_ONLY_CHEST =
            REGISTRY.register("take_only_chest", () -> new MenuType<>(TakeOnlyChestMenu::new));
}
