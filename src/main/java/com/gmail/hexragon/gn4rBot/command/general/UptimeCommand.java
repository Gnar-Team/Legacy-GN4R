package com.gmail.hexragon.gn4rBot.command.general;

import com.gmail.hexragon.gn4rBot.GnarBot;
import com.gmail.hexragon.gn4rBot.managers.commands.annotations.Command;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.util.GnarMessage;

@Command(
		aliases = "uptime",
		description = "Show the bot's uptime."
)
public class UptimeCommand extends CommandExecutor
{
	@Override
	public void execute(GnarMessage message, String[] args)
	{
		message.reply("I've been up and awake for `"+ GnarBot.getUptimeStamp() + "`.");
	}
}
