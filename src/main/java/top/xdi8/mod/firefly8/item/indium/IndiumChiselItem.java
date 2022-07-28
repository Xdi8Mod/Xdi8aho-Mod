package top.xdi8.mod.firefly8.item.indium;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import org.jetbrains.annotations.NotNull;

public class IndiumChiselItem extends Item {
    public IndiumChiselItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext pContext) {
        return InteractionResult.PASS;
    }
}
