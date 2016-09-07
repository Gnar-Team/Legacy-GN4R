package com.gmail.hexragon.gn4rBot.command.admin;

import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.managers.commands.annotations.Command;
import com.gmail.hexragon.gn4rBot.managers.commands.annotations.GuildDependent;
import com.gmail.hexragon.gn4rBot.managers.users.PermissionLevel;
import com.gmail.hexragon.gn4rBot.util.GnarMessage;
import net.dv8tion.jda.entities.User;

import java.util.Arrays;

@Deprecated
@GuildDependent
@Command(
        aliases = {"assignperm", "reassignperm", "reassignpermission"},
        usage = "(@user) (perm)",
        description = "Change user's Gn4r-Bot permission.",
        permissionRequired = PermissionLevel.SERVER_OWNER
)
public class ReassignPermissionCommand extends CommandExecutor
{
    @Override
    public void execute(GnarMessage message, String[] args)
    {
        if (args.length >= 2)
        {
            User target = message.getMentionedUsers().get(0);
            
            if (target == null || (target.isBot()))
            {
                message.reply("You did not mention a user. *(Can not be a bot either)*");
                return;
            }
            
            for (PermissionLevel permissionLevel : PermissionLevel.serverValues())
            {
                if (permissionLevel.toString().toLowerCase().equals(args[1].toLowerCase()))
                {
                    if (getGuildManager().getUserManager().getGnarUser(message.getAuthor()).getPermission().value < permissionLevel.value)
                    {
                        message.reply("You need to be `" + permissionLevel.toString() + "` to assign that permission.");
                        return;
                    }
                    getGuildManager().getUserManager().getGnarUser(message.getAuthor()).setGnarPermission(permissionLevel);
                    message.getChannel().sendMessage(String.format("%s ➜ You have set %s's Gn4r-Bot permission to `%s`.", message.getAuthor().getAsMention(), target.getAsMention(), permissionLevel.toString()));
                    return;
                }
            }
            message.getChannel().sendMessage(String.format("%s ➜ Permission not found. Valid permissions are: ```%s```", message.getAuthor().getAsMention(), Arrays.toString(PermissionLevel.serverValues())));
        }
        else
        {
            message.reply("Insufficient amount of arguments.");
        }
    }
}
