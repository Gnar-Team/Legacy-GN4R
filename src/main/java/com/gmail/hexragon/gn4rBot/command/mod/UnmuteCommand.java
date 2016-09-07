package com.gmail.hexragon.gn4rBot.command.mod;

import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.managers.commands.annotations.Command;
import com.gmail.hexragon.gn4rBot.managers.commands.annotations.GuildDependent;
import com.gmail.hexragon.gn4rBot.managers.users.PermissionLevel;
import com.gmail.hexragon.gn4rBot.util.GnarMessage;
import net.dv8tion.jda.entities.User;
import net.dv8tion.jda.exceptions.PermissionException;

@GuildDependent
@Command(
        aliases = {"unmute"},
        usage = "(@user)",
        description = "Allow users to use chat again.",
        permissionRequired = PermissionLevel.BOT_COMMANDER
)
public class UnmuteCommand extends CommandExecutor
{
    @Override
    public void execute(GnarMessage message, String[] args)
    {
        if (message.getMentionedUsers().size() < 1)
        {
            message.reply("You did not mention a user.");
            return;
        }
        
        User target = message.getMentionedUsers().get(0);
        
        try
        {
            getGuildManager().unmute(target);
        }
        catch (PermissionException e)
        {
            message.reply("GN4R does not have sufficient permission to unmute target.");
        }
        message.reply("You have unmuted " + target.getAsMention() + ".");
    }
}
