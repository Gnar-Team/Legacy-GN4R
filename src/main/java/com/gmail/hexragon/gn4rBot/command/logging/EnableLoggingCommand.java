package com.gmail.hexragon.gn4rBot.command.logging;

import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.managers.commands.annotations.Command;
import com.gmail.hexragon.gn4rBot.managers.commands.annotations.GuildDependent;
import com.gmail.hexragon.gn4rBot.managers.commands.annotations.ManagerDependent;
import com.gmail.hexragon.gn4rBot.util.GnarMessage;

import static com.gmail.hexragon.gn4rBot.managers.users.PermissionLevel.BOT_MASTER;

/**
 * Created by Mae on 9/22/2016.
 */
@GuildDependent
@ManagerDependent
@Command(
        aliases = {"enablelogging", "el"},
        description = "Automatically update GN4R",
        permissionRequired = BOT_MASTER,
        showInHelp = false
)
public class EnableLoggingCommand extends CommandExecutor {

    @Override
    public void execute(GnarMessage message, String[] args) {

        if(args[0].equals("join-message-toggle") || args[0].equals("leave-message-toggle")) {

            if(args[1].equals("enable") || args[1].equals("disable")) {

                getGuildManager().editValue(args[0], args[1]);

                message.replyRaw("Changed **" + args[0] + "** to **" + args[1] + "**");
            } else {
                message.reply("\n**Possible Options:** `enable`, `disable`");
            }
        } else {
            message.reply("\n**Possible Options:** `join-message-toggle`, `leave-message-toggle`");
        }
    }
}