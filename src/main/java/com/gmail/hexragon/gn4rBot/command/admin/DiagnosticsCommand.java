package com.gmail.hexragon.gn4rBot.command.admin;

import com.gmail.hexragon.gn4rBot.managers.commands.annotations.Command;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.util.GnarMessage;
import com.gmail.hexragon.gn4rBot.util.SystemInfo;

import static com.gmail.hexragon.gn4rBot.managers.users.PermissionLevel.BOT_MASTER;

@Command(
		aliases = {"diag", "diagnostics", "memory"},
		description = "Show current memory usage of GN4R.",
		permissionRequired = BOT_MASTER,
		showInHelp = false
)
public class DiagnosticsCommand extends CommandExecutor
{
	@Override
	public void execute(GnarMessage message, String[] args)
	{
		message.replyRaw("```xl\n"+new SystemInfo().osInfo()+"```\n```xl\n"+new SystemInfo().memInfo()+"```");
		
		//getCommandManager().getCommandRegistry().forEach((str, cmd) -> System.out.println(str + "\t" + cmd.hashCode()));
	}
}