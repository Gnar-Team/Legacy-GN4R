package com.gmail.hexragon.gn4rBot.command.fun;

import com.gmail.hexragon.gn4rBot.managers.commands.Command;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import net.dv8tion.jda.entities.Message;

@Command(
		aliases = {"discordgold"},
		description = "Tilts dumb people."
)
public class DiscordGoldCommand extends CommandExecutor
{
	@Override
	public void execute(Message message, String[] args)
	{
		message.deleteMessage();
		message.getChannel().sendMessage("```xl\nDiscord Gold is required to view this message.\n```");
	}
}
