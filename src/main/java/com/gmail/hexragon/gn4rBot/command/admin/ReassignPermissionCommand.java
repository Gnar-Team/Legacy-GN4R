package com.gmail.hexragon.gn4rBot.command.admin;

import com.gmail.hexragon.gn4rBot.managers.commands.Command;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.managers.users.PermissionLevel;
import net.dv8tion.jda.entities.Message;
import net.dv8tion.jda.entities.User;

import java.util.Arrays;


@Command(
		aliases = {"assignperm", "reassignperm", "reassignpermission"},
		usage = "(@user) (perm)",
		description = "Change user's Gn4r-Bot permission.",
		permissionRequired = PermissionLevel.SERVER_OWNER
)
public class ReassignPermissionCommand extends CommandExecutor
{
	@Override
	public void execute(Message message, String[] args)
	{
		if (args.length >= 2)
		{
			User target = message.getMentionedUsers().get(0);
			
			if (target == null || (target.isBot()))
			{
				message.getChannel().sendMessage(String.format("%s ➜ You did not mention a user.%s", message.getAuthor().getAsMention(), target != null && target.isBot() ? " (Can't be a bot)" : ""));
				return;
			}
			
			for (PermissionLevel permissionLevel : PermissionLevel.serverValues())
			{
				if (permissionLevel.toString().toLowerCase().equals(args[1].toLowerCase()))
				{
					if (getGnarGuild().getUserManager().getGnarUser(message.getAuthor()).getPermission().value < permissionLevel.value)
					{
						message.getChannel().sendMessage(String.format("%s ➜ You need to be `%s` to assign that permission.", message.getAuthor().getAsMention(), permissionLevel.toString()));
						return;
					}
					getGnarGuild().getUserManager().getGnarUser(message.getAuthor()).setGnarPermission(permissionLevel);
					message.getChannel().sendMessage(String.format("%s ➜ You have set %s's Gn4r-Bot permission to `%s`.", message.getAuthor().getAsMention(), target.getAsMention(), permissionLevel.toString()));
					return;
				}
			}
			message.getChannel().sendMessage(String.format("%s ➜ Permission not found. Valid permissions are: ```%s```", message.getAuthor().getAsMention(), Arrays.toString(PermissionLevel.serverValues())));
		}
		else
		{
			message.getChannel().sendMessage(message.getAuthor().getAsMention() + " ➜ Insufficient amount of arguments.");
		}
	}
}
