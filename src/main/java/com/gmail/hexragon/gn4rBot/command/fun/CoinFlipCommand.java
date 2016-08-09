package com.gmail.hexragon.gn4rBot.command.fun;

import com.gmail.hexragon.gn4rBot.managers.commands.annotations.Command;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.util.GnarMessage;

import java.util.Random;

@Command(
		aliases = {"coinflip"},
		description = "Heads or Tails?"
)
public class CoinFlipCommand extends CommandExecutor
{
	@Override
	public void execute(GnarMessage message, String[] args)
	{
		if (new Random().nextInt(2) == 0)
		{
			message.reply("`Heads`!");
		}
		else
		{
			message.reply("`Tails`!");
		}
	}
}
