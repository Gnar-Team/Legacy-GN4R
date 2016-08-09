package com.gmail.hexragon.gn4rBot.command.fun;

import com.gmail.hexragon.gn4rBot.managers.commands.annotations.Command;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.util.GnarMessage;

@Command(
		aliases = {"discordgold"},
		description = "Tilts dumb people."
)
public class DiscordGoldCommand extends CommandExecutor
{
	@Override
	public void execute(GnarMessage message, String[] args)
	{
		try
		{
			message.deleteMessage();
		}
		catch (Exception ignore) {}
		message.replyRaw("```xl\nDiscord Gold is required to view this message.\n```");
	}
}
