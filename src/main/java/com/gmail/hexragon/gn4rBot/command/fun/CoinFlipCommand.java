package com.gmail.hexragon.gn4rBot.command.fun;

import com.gmail.hexragon.gn4rBot.managers.commands.Command;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import net.dv8tion.jda.entities.Message;

import java.util.Random;

@Command(
		aliases = {"coinflip"},
		description = "Heads or Tails?"
)
public class CoinFlipCommand extends CommandExecutor
{
	@Override
	public void execute(Message message, String[] args)
	{
		if (new Random().nextInt(2) == 0)
		{
			message.getChannel().sendMessage(String.format("%s ➜ `Heads`!", message.getAuthor().getAsMention()));
		}
		else
		{
			message.getChannel().sendMessage(String.format("%s ➜ `Tails`!", message.getAuthor().getAsMention()));
		}
	}
}
