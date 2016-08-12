package com.gmail.hexragon.gn4rBot.command.mod;

import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.managers.commands.annotations.Command;
import com.gmail.hexragon.gn4rBot.managers.commands.annotations.RequiresGuild;
import com.gmail.hexragon.gn4rBot.managers.guildMessage.GuildManager;
import com.gmail.hexragon.gn4rBot.managers.users.PermissionLevel;
import com.gmail.hexragon.gn4rBot.util.GnarMessage;
import net.dv8tion.jda.entities.User;
import net.dv8tion.jda.exceptions.PermissionException;

import java.util.List;

@RequiresGuild
@Command(
        aliases = {"unban"},
        usage = "(user-id)",
        description = "Unban users.",
        permissionRequired = PermissionLevel.BOT_COMMANDER
)
public class UnbanCommand extends CommandExecutor
{
    @Override
    public void execute(GnarMessage message, String[] args)
    {
        if (args.length == 0)
        {
            message.reply("You did not provide a user ID.");
            return;
        }
        
        List<User> bannedList = ((GuildManager) getGnarManager()).getBans();
        
        User target = null;
        
        for (User user : bannedList)
        {
            if (user.getId().equals(args[0]))
            {
                target = user;
            }
        }
        
        if (target == null)
        {
            message.reply("That is not a valid user ID.");
            return;
        }
        
        try
        {
            ((GuildManager) getGnarManager()).unBan(target);
        }
        catch (PermissionException e)
        {
            message.reply("GN4R does not have sufficient permission to unban target.");
        }
        message.reply("You have unbanned " + target.getAsMention() + ".");
    }
}
