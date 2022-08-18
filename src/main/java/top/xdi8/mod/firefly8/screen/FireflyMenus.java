package top.xdi8.mod.firefly8.screen;

import net.minecraft.world.inventory.MenuType;
import org.featurehouse.mcmod.spm.platform.api.reg.PlatformRegister;
import top.xdi8.mod.firefly8.util.InternalRegistryLogWrapper;

import java.util.function.Supplier;

public class FireflyMenus {
    private static final PlatformRegister reg = PlatformRegister.of("firefly8");
    public static final InternalRegistryLogWrapper LOG_WRAPPER = InternalRegistryLogWrapper.firefly8("menus");

    public static final Supplier<MenuType<TakeOnlyChestMenu>> TAKE_ONLY_CHEST =
            reg.menu("take_only_chest", TakeOnlyChestMenu::new);
    public @Deprecated static final Supplier<MenuType<ChiselMenu>> CHISEL =
            reg.menu("chisel", ChiselMenu::new);
    public static final Supplier<MenuType<Xdi8TableMenu>> XDI8_TABLE =
            reg.menu("xdi8_table", Xdi8TableMenu::new);

}
