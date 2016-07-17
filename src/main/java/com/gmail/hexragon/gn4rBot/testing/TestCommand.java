package com.gmail.hexragon.gn4rBot.testing;

import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandManager;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

public class TestCommand extends CommandExecutor
{
	public TestCommand(CommandManager manager)
	{
		super(manager);
		showInHelp(false);
	}

	@Override
	public void execute(MessageReceivedEvent event, String[] args)
	{

	}
}
