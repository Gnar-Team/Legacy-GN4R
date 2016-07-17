package com.gmail.hexragon.gn4rBot.command.fun;

import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandManager;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

import java.util.Random;

public class RollCommand extends CommandExecutor
{
	public RollCommand(CommandManager manager)
	{
		super(manager);
		setDescription("Roll a random number from 0 to -arg#0.");
		setUsage("rand (integer)");
	}

	@Override
	public void execute(MessageReceivedEvent event, String[] args)
	{
		if (args.length >= 1)
		{
			if (!(Integer.valueOf(args[0]) > 0))
			{
				event.getChannel().sendMessage(String.format("%s ➤ Number need to be > 0", event.getAuthor().getAsMention()));

				return;
			}
			event.getChannel().sendMessage(String.format("%s ➤ You rolled `%d` from range `0 to %3$s`.", event.getAuthor().getAsMention(), new Random().nextInt(Integer.valueOf(args[0])), args[0]));
		}
		else
		{
			event.getChannel().sendMessage(String.format("%s ➤ Insufficient amount of arguments.", event.getAuthor().getAsMention()));
		}
	}
}
