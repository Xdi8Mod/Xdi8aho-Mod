package top.xdi8.mod.firefly8.screen;

import dev.architectury.registry.registries.RegistrySupplier;
import io.github.qwerty770.mcmod.xdi8.util.registries.RegistryHelper;
import net.minecraft.world.inventory.MenuType;
import io.github.qwerty770.mcmod.xdi8.api.InternalRegistryLogWrapper;

public class FireflyMenus {
    public static final InternalRegistryLogWrapper LOG_WRAPPER = InternalRegistryLogWrapper.firefly8("menus");

    public static final RegistrySupplier<MenuType<TakeOnlyChestMenu>> TAKE_ONLY_CHEST =
            RegistryHelper.simpleMenuType("take_only_chest", TakeOnlyChestMenu::new);
    public static final RegistrySupplier<MenuType<Xdi8TableMenu>> XDI8_TABLE =
            RegistryHelper.simpleMenuType("xdi8_table", Xdi8TableMenu::new);

}
