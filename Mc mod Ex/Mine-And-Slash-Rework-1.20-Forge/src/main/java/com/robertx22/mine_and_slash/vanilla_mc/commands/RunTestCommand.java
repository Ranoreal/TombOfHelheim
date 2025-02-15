package com.robertx22.mine_and_slash.vanilla_mc.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.robertx22.mine_and_slash.uncommon.testing.CommandTests;
import com.robertx22.mine_and_slash.vanilla_mc.commands.suggestions.CommandsSuggestions;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

public class RunTestCommand {
    public static void register(CommandDispatcher<CommandSourceStack> commandDispatcher) {
        commandDispatcher.register(
                literal(CommandRefs.ID)
                        .then(literal("runtest").requires(e -> e.hasPermission(2))
                                .then(argument("test", StringArgumentType.string()).suggests(new CommandsSuggestions())
                                        .executes(
                                                ctx -> run(ctx.getSource()
                                                        .getPlayerOrException(), StringArgumentType.getString(ctx, "test"))))));
    }

    private static int run(ServerPlayer player, String test) {

        CommandTests.run(test, player);

        player.displayClientMessage(Component.literal("Test completed."), false);

        return 1;
    }
}
