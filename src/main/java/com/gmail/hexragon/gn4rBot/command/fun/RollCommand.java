package com.gmail.hexragon.gn4rBot.command.fun;

import com.gmail.hexragon.gn4rBot.managers.commands.Command;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import net.dv8tion.jda.entities.Message;

import java.util.Random;

@Command(
		aliases = {"roll"},
		usage = "(integer)",
		description = "Roll a random number from 0 to argument."
)
public class RollCommand extends CommandExecutor
{
	
	@Override
	public void execute(Message message, String[] args)
	{
		if (args.length >= 1)
		{
			if (!(Integer.valueOf(args[0]) > 0))
			{
				message.getChannel().sendMessage(String.format("%s ➜ Number need to be > 0", message.getAuthor().getAsMention()));
				
				return;
			}
			message.getChannel().sendMessage(String.format("%s ➜ You rolled `%d` from range `0 to %3$s`.", message.getAuthor().getAsMention(), new Random().nextInt(Integer.valueOf(args[0])), args[0]));
		}
		else
		{
			message.getChannel().sendMessage(String.format("%s ➜ Insufficient amount of arguments.", message.getAuthor().getAsMention()));
		}
	}
}
