package org.featurehouse.mcfgmod.firefly8.item;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BottleItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BeehiveBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

public class TintedGlassBottleItem extends BottleItem {

    public TintedGlassBottleItem(Properties p_40648_) {
        super(p_40648_);
    }
    
    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        var ret = super.use(pLevel, pPlayer, pHand);
        ItemStack stack = ret.getObject();
        if (stack.is(Items.POTION)) {
            return new InteractionResultHolder<ItemStack>(ret.getResult(), 
                new ItemStack(FireflyItems.TINTED_POTION.get(), stack.getCount(), stack.getTag()));
        }
        return ret;
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        BlockState state = pContext.getLevel().getBlockState(pContext.getClickedPos());
        if (!state.is(BlockTags.BEEHIVES) || !(state.hasProperty(BeehiveBlock.HONEY_LEVEL))) {
            return InteractionResult.PASS;
        }
        int honeyLevel = state.getValue(BeehiveBlock.HONEY_LEVEL);
        if (honeyLevel >= 5) {
            Player player = pContext.getPlayer();
            InteractionHand hand = pContext.getHand();
            ItemStack stack = player.getItemInHand(hand);

            stack.shrink(1);
            pContext.getLevel().playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.BOTTLE_FILL,
                SoundSource.NEUTRAL, 1.0F, 1.0F);
            if (stack.isEmpty()) {
                player.setItemInHand(hand, new ItemStack(FireflyItems.TINTED_HONEY_BOTTLE.get()));
            } else if (!player.getInventory().add(new ItemStack(FireflyItems.TINTED_HONEY_BOTTLE.get()))) {
                player.drop(new ItemStack(FireflyItems.TINTED_HONEY_BOTTLE.get()), false);
            }
            pContext.getLevel().gameEvent(player, GameEvent.FLUID_PICKUP, pContext.getClickedPos());

            if (!pContext.getLevel().isClientSide()) {
                
            }
        }
    }
}
