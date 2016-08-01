package com.gmail.hexragon.gn4rBot.command.general;

import com.gmail.hexragon.gn4rBot.managers.commands.Command;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import net.dv8tion.jda.entities.Message;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;

@Command(
		aliases = "ping",
		description = "Show the bot's current response time.."
)
public class PingCommand extends CommandExecutor
{
	@Override
	public void execute(Message message, String[] args)
	{
		OffsetDateTime sentTime = message.getTime();
		OffsetDateTime responseTime = OffsetDateTime.now();
		
		message.getChannel().sendMessage(message.getAuthor().getAsMention() + " ➤ Response time: `"+ Math.abs(sentTime.until(responseTime, ChronoUnit.MILLIS)) + "ms`.");
	}
}
