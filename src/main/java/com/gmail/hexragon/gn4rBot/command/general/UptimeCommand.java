package com.gmail.hexragon.gn4rBot.command.general;

import com.gmail.hexragon.gn4rBot.GnarBot;
import com.gmail.hexragon.gn4rBot.managers.commands.Command;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import net.dv8tion.jda.entities.Message;

@Command(
		aliases = "uptime",
		description = "Show the bot's uptime."
)
public class UptimeCommand extends CommandExecutor
{
	@Override
	public void execute(Message message, String[] args)
	{
		message.getChannel().sendMessage(message.getAuthor().getAsMention() + " ➤ I've been up and awake for `"+ GnarBot.getUptimeStamp() + "`.");
	}
}
