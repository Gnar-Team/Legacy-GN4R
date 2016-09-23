package com.gmail.hexragon.gn4rBot.command.logging;

import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.managers.commands.annotations.Command;
import com.gmail.hexragon.gn4rBot.managers.commands.annotations.GuildDependent;
import com.gmail.hexragon.gn4rBot.managers.commands.annotations.ManagerDependent;
import com.gmail.hexragon.gn4rBot.util.GnarMessage;
import net.dv8tion.jda.entities.TextChannel;

import static com.gmail.hexragon.gn4rBot.managers.users.PermissionLevel.BOT_MASTER;

@GuildDependent
@ManagerDependent
@Command(
        aliases = {"logchannel", "lc"},
        description = "Automatically update GN4R",
        permissionRequired = BOT_MASTER,
        showInHelp = true
)
public class LoggingChannelCommand extends CommandExecutor {

    @Override
    public void execute(GnarMessage message, String[] args) {

        boolean found = false;

        for(TextChannel t : getGuild().getTextChannels()) {
            if(t.getName().equals(args[0])) {
                found = true;
                getGuildManager().editValue("log-channel", args[0]);
                message.replyRaw("Log channel has been set to: **" + args[0] + "**");
            }
        }

        if(!found) {
            message.reply("Error: Channel not found");
        }
    }
}