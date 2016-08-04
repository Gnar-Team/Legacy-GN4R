package com.gmail.hexragon.gn4rBot.command.admin;

import com.gmail.hexragon.gn4rBot.managers.commands.Command;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.util.GnarMessage;

import static com.gmail.hexragon.gn4rBot.managers.users.PermissionLevel.SERVER_OWNER;

@Command(
		aliases = {"asigntoken", "reassigntoken", "rtoken"},
		usage = "(string)",
		description = "Changes Gn4r-Bot's command token.",
		permissionRequired = SERVER_OWNER
)
public class ReassignTokenCommand extends CommandExecutor
{
	@Override
	public void execute(GnarMessage message, String[] args)
	{
		if (args.length >= 1)
		{
			getCommandManager().setToken(args[0]);
			message.reply("GN4R-Bot's command token has been changed to `"+args[0]+"`.");
		}
		else
		{
			message.reply("Insufficient amount of arguments.");
		}
	}
}
