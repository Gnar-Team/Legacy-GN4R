package com.gmail.hexragon.gn4rBot.command.admin;


import com.gmail.hexragon.gn4rBot.managers.commands.Command;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import net.dv8tion.jda.entities.Message;

import static com.gmail.hexragon.gn4rBot.managers.users.PermissionLevel.SERVER_OWNER;

@Command(
		aliases = {"reassigntoken", "rtoken"},
		usage = "(string)",
		description = "Changes Gn4r-Bot's command token.",
		permissionRequired = SERVER_OWNER
)
public class ReassignTokenCommand extends CommandExecutor
{
	@Override
	public void execute(Message message, String[] args)
	{
		if (args.length >= 1)
		{
			getCommandManager().setToken(args[0]);
			message.getChannel().sendMessage(message.getAuthor().getAsMention() + " ➤ GN4R-Bot's command token has been changed to `"+args[0]+"`.");
		}
		else
		{
			message.getChannel().sendMessage(message.getAuthor().getAsMention() + " ➤ Insufficient amount of arguments.");
		}
	}
}
