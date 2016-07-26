package com.gmail.hexragon.gn4rBot.command.fun;

import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandManager;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

import java.util.Random;

public class CoinFlipCommand extends CommandExecutor
{
	public CoinFlipCommand(CommandManager manager)
	{
		super(manager);
		setDescription("Heads or Tails?");
	}
	
	@Override
	public void execute(MessageReceivedEvent event, String[] args)
	{
		if (new Random().nextInt(2) == 0)
		{
			event.getChannel().sendMessage(String.format("%s ➤ `Heads`!", event.getAuthor().getAsMention()));
		}
		else
		{
			event.getChannel().sendMessage(String.format("%s ➤ `Tails`!", event.getAuthor().getAsMention()));
		}
	}
}
