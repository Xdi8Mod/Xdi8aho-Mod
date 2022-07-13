package top.xdi8.mod.firefly8.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.DimensionArgument;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.xdi8.mod.firefly8.ext.IPlayerWithHiddenInventory;

@Mod.EventBusSubscriber(modid = "firefly8")
public final class FireflyCommands {
    @SubscribeEvent
    public static void init(RegisterCommandsEvent event) {
        final LiteralArgumentBuilder<CommandSourceStack> command =
                Commands.literal("bindxdi8portal")
                        .requires((CommandSourceStack src) -> src.hasPermission(2))
                        .then(Commands.argument("pos", BlockPosArgument.blockPos())
                                .executes(BindXdi8PortalCommand::init0)
                                .then(Commands.argument("player", EntityArgument.player())
                                        .executes(BindXdi8PortalCommand::init1)
                                        .then(Commands.literal("at")
                                                .then(Commands.argument("dimension", DimensionArgument.dimension())
                                                        .executes(BindXdi8PortalCommand::initAll)
                                                )
                                        ))
                                .then(Commands.literal("at")
                                        .then(Commands.argument("dimension", DimensionArgument.dimension())
                                                .executes(BindXdi8PortalCommand::init2)
                                        )
                                )
                        );
        event.getDispatcher().register(command);
    }

    private static final class BindXdi8PortalCommand {
        private static int execute(ResourceKey<Level> level, BlockPos pos, ServerPlayer player, CommandSourceStack stack) {
            final IPlayerWithHiddenInventory playerExt = IPlayerWithHiddenInventory.xdi8$extend(player);
            if (playerExt.xdi8$validatePortal()) {
                playerExt.xdi8$setPortal(level, pos);
                stack.sendSuccess(new TranslatableComponent("commands.bindxdi8portal.success", player.getDisplayName()), true);
                return 1;
            } else {
                stack.sendFailure(new TranslatableComponent("commands.bindxdi8portal.failure", player.getDisplayName(), level.location(), pos.toShortString()));
                return 0;
            }
        }

        static int init0(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
            BlockPos pos = BlockPosArgument.getLoadedBlockPos(ctx, "pos");
            final ServerPlayer player = ctx.getSource().getPlayerOrException();
            return execute(Level.OVERWORLD, pos, player, ctx.getSource());
        }

        static int init1(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
            BlockPos pos = BlockPosArgument.getLoadedBlockPos(ctx, "pos");
            ServerPlayer player = EntityArgument.getPlayer(ctx, "player");
            return execute(Level.OVERWORLD, pos, player, ctx.getSource());
        }

        static int init2(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
            BlockPos pos = BlockPosArgument.getLoadedBlockPos(ctx, "pos");
            ServerPlayer player = ctx.getSource().getPlayerOrException();
            var dimension = DimensionArgument.getDimension(ctx, "dimension");
            return execute(dimension.dimension(), pos, player, ctx.getSource());
        }

        static int initAll(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
            BlockPos pos = BlockPosArgument.getLoadedBlockPos(ctx, "pos");
            ServerPlayer player = EntityArgument.getPlayer(ctx, "player");
            var dimension = DimensionArgument.getDimension(ctx, "dimension");
            return execute(dimension.dimension(), pos, player, ctx.getSource());
        }
    }
}
