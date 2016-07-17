package com.gmail.hexragon.gn4rBot.command.fun;

import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandManager;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

public class DiscordGoldCommand extends CommandExecutor
{
	public DiscordGoldCommand(CommandManager manager)
	{
		super(manager);
		setDescription("Tilts dumb people.");
	}

	@Override
	public void execute(MessageReceivedEvent event, String[] args)
	{
		event.getMessage().deleteMessage();
		event.getChannel().sendMessage("```xl\nDiscord Gold is required to view this message.\n```");
	}
}
