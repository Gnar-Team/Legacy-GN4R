package com.gmail.hexragon.gn4rBot.command.admin;

import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandManager;
import com.gmail.hexragon.gn4rBot.managers.users.PermissionLevel;
import net.dv8tion.jda.entities.User;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

import java.util.Arrays;

public class ReassignPermissionCommand extends CommandExecutor
{
	public ReassignPermissionCommand()
	{
		
		setDescription("Change user's Gn4r-Bot permission.");
		setUsage("(@user) (perm)");
		setPermission(PermissionLevel.SERVER_OWNER);
	}
	
	@Override
	public void execute(MessageReceivedEvent event, String[] args)
	{
		if (args.length >= 2)
		{
			User target = event.getMessage().getMentionedUsers().get(0);
			
			if (target == null || (target.isBot()))
			{
				event.getChannel().sendMessage(String.format("%s ➤ You did not mention a user.%s", event.getAuthor().getAsMention(), target != null && target.isBot() ? " (Can't be a bot)" : ""));
				return;
			}
			
			for (PermissionLevel permissionLevel : PermissionLevel.serverValues())
			{
				if (permissionLevel.toString().toLowerCase().equals(args[1].toLowerCase()))
				{
					if (getGnarGuild().getUserManager().getGnarUser(event.getAuthor()).getPermission().value < permissionLevel.value)
					{
						event.getChannel().sendMessage(String.format("%s ➤ You need to be `%s` to assign that permission.", event.getAuthor().getAsMention(), permissionLevel.toString()));
						return;
					}
					getGnarGuild().getUserManager().getGnarUser(event.getAuthor()).setGnarPermission(permissionLevel);
					event.getChannel().sendMessage(String.format("%s ➤ You have set %s's Gn4r-Bot permission to `%s`.", event.getAuthor().getAsMention(), target.getAsMention(), permissionLevel.toString()));
					return;
				}
			}
			event.getChannel().sendMessage(String.format("%s ➤ Permission not found. Valid permissions are: ```%s```", event.getAuthor().getAsMention(), Arrays.toString(PermissionLevel.serverValues())));
		}
		else
		{
			event.getChannel().sendMessage(String.format("%s ➤ Insufficient amount of arguments.", event.getAuthor().getAsMention()));
		}
	}
}
