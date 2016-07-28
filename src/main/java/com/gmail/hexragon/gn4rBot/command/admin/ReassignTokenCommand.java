package com.gmail.hexragon.gn4rBot.command.admin;


import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandManager;
import com.gmail.hexragon.gn4rBot.managers.users.PermissionLevel;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

public class ReassignTokenCommand extends CommandExecutor
{
	public ReassignTokenCommand()
	{
		
		setDescription("Changes Gn4r-Bot's command token.");
		setUsage("(string)");
		setPermission(PermissionLevel.SERVER_OWNER);
	}
	
	@Override
	public void execute(MessageReceivedEvent event, String[] args)
	{
		if (args.length >= 1)
		{
			getCommandManager().setToken(args[0]);
			event.getChannel().sendMessage(event.getAuthor().getAsMention() + " ➤ GN4R-Bot's command token has been changed to `"+args[0]+"`.");
		}
		else
		{
			event.getChannel().sendMessage(event.getAuthor().getAsMention() + " ➤ Insufficient amount of arguments.");
		}
	}
}
