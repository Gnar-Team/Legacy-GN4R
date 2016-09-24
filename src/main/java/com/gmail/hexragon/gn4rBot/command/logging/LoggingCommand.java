package com.gmail.hexragon.gn4rBot.command.logging;

import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.managers.commands.annotations.Command;
import com.gmail.hexragon.gn4rBot.managers.commands.annotations.GuildDependent;
import com.gmail.hexragon.gn4rBot.managers.commands.annotations.ManagerDependent;
import com.gmail.hexragon.gn4rBot.util.GnarMessage;

import static com.gmail.hexragon.gn4rBot.managers.users.PermissionLevel.SERVER_OWNER;

@GuildDependent
@ManagerDependent
@Command(
        aliases = {"loggingmessages", "lm"},
        description = "Change Logging messages",
        permissionRequired = SERVER_OWNER,
        showInHelp = true
)
public class LoggingCommand extends CommandExecutor {

    @Override
    public void execute(GnarMessage message, String[] args) {

        if(args[0].equals("join-message") || args[0].equals("leave-message")) {

            String newValue = "";
            for (int i = 1; i < args.length; i++) {
                newValue += args[i] + " ";
            }

            getGuildManager().editValue(args[0], newValue);

            message.replyRaw("Changed **" + args[0] + "** to **" + newValue + "**");
        } else {
            message.reply("\n**Possible Options:** `join-message`, `leave-message`");
        }
    }
}