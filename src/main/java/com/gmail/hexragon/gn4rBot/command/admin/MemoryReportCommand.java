package com.gmail.hexragon.gn4rBot.command.admin;

import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandManager;
import com.gmail.hexragon.gn4rBot.managers.users.PermissionLevel;
import com.gmail.hexragon.gn4rBot.util.SystemInfo;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

public class MemoryReportCommand extends CommandExecutor
{
	public MemoryReportCommand(CommandManager manager)
	{
		super(manager);
		setPermission(PermissionLevel.BOT_MASTER);
		showInHelp(false);
	}
	
	@Override
	public void execute(MessageReceivedEvent event, String[] args)
	{
		event.getChannel().sendMessage("```xl\n"+new SystemInfo().memInfo()+"```");
		
		//getCommandManager().getCommandRegistry().forEach((str, cmd) -> System.out.println(str + "\t" + cmd.hashCode()));
	}
}