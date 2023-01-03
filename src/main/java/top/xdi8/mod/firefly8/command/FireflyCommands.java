package top.xdi8.mod.firefly8.command;

import com.mojang.brigadier.CommandDispatcher;
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
import top.xdi8.mod.firefly8.ext.IServerPlayerWithHiddenInventory;

import java.util.Collection;
import java.util.Collections;

public final class FireflyCommands {

    public static void init(CommandDispatcher<CommandSourceStack> dispatcher,
                            Commands.CommandSelection selection) {
        if (selection != Commands.CommandSelection.ALL) return;
        LiteralArgumentBuilder<CommandSourceStack> command =
                Commands.literal("bindxdi8portal")
                        .requires((CommandSourceStack src) -> src.hasPermission(2))
                        .then(Commands.argument("pos", BlockPosArgument.blockPos())
                                .executes(BindXdi8PortalCommand::init0)
                                .then(Commands.literal("for")
                                        .then(Commands.argument("player", EntityArgument.player())
                                                .executes(BindXdi8PortalCommand::init1)
                                                .then(Commands.literal("at")
                                                        .then(Commands.argument("dimension", DimensionArgument.dimension())
                                                                .executes(BindXdi8PortalCommand::initAll)
                                                        )
                                                ))
                                )  // if not, Player "at" can't bind the portal!
                                .then(Commands.literal("at")
                                        .then(Commands.argument("dimension", DimensionArgument.dimension())
                                                .executes(BindXdi8PortalCommand::init2)
                                        )
                                )
                        );
        dispatcher.register(command);
        command = Commands.literal("unbindxdi8portal")
                .requires((CommandSourceStack src) -> src.hasPermission(2))
                .executes(UnbindXdi8PortalCommand::unbind1)
                .then(Commands.argument("players", EntityArgument.players())
                        .executes(UnbindXdi8PortalCommand::unbind0)
                );
        dispatcher.register(command);
    }

    private static final class UnbindXdi8PortalCommand {
        private static int execute(Collection<ServerPlayer> players, CommandSourceStack stack) {
            final int count = players.size();
            if (count == 0) {
                stack.sendFailure(new TranslatableComponent("commands.unbindxdi8portal.0"));
                return 0;
            }

            for (ServerPlayer player : players) {
                final IServerPlayerWithHiddenInventory ext = IServerPlayerWithHiddenInventory.xdi8$extend(player);
                ext.xdi8$setPortal(null, BlockPos.ZERO);
                if (count == 1) {
                    stack.sendSuccess(new TranslatableComponent("commands.unbindxdi8portal.1", player.getDisplayName()), true);
                }
            }
            if (count != 1) {
                stack.sendSuccess(new TranslatableComponent("commands.unbindxdi8portal.more", count), true);
            }
            return 1;
        }

        static int unbind0(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
            final Collection<ServerPlayer> players = EntityArgument.getPlayers(ctx, "players");
            return execute(players, ctx.getSource());
        }

        static int unbind1(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
            final ServerPlayer player = ctx.getSource().getPlayerOrException();
            return execute(Collections.singleton(player), ctx.getSource());
        }
    }

    private static final class BindXdi8PortalCommand {
        private static int execute(ResourceKey<Level> level, BlockPos pos, ServerPlayer player, CommandSourceStack stack) {
            final IServerPlayerWithHiddenInventory playerExt = IServerPlayerWithHiddenInventory.xdi8$extend(player);
            if (playerExt.xdi8$validatePortal(level, pos)) {
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
