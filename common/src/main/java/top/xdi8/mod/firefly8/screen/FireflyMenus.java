package top.xdi8.mod.firefly8.screen;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.inventory.MenuType;
import org.featurehouse.mcmod.spm.platform.api.reg.PlatformRegister;
import top.xdi8.mod.firefly8.util.InternalRegistryLogWrapper;

public class FireflyMenus {
    private static final PlatformRegister reg = PlatformRegister.of("firefly8");
    public static final InternalRegistryLogWrapper LOG_WRAPPER = InternalRegistryLogWrapper.firefly8("menus");

    public static final RegistrySupplier<MenuType<TakeOnlyChestMenu>> TAKE_ONLY_CHEST =
            reg.menu("take_only_chest", TakeOnlyChestMenu::new);
    public static final RegistrySupplier<MenuType<Xdi8TableMenu>> XDI8_TABLE =
            reg.menu("xdi8_table", Xdi8TableMenu::new);

}
