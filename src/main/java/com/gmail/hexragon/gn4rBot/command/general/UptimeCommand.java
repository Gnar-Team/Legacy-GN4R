package com.gmail.hexragon.gn4rBot.command.general;

import com.gmail.hexragon.gn4rBot.GnarBot;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandManager;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

public class UptimeCommand extends CommandExecutor
{
	public UptimeCommand(CommandManager manager)
	{
		super(manager);
		setDescription("Show bot's uptime.");
	}
	
	@Override
	public void execute(MessageReceivedEvent event, String[] args)
	{
		event.getChannel().sendMessage(event.getAuthor().getAsMention() + " ➤ I've been up and awake for `"+ GnarBot.getUptimeStamp() + "`.");
	}
}
