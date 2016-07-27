package com.gmail.hexragon.gn4rBot.command.fun;

import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandManager;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

import java.util.Random;

public class EightBallCommand extends CommandExecutor
{
	private final Random random = new Random();
	private final String[] responses =
			{
					"It is certain",
					"It is decidedly so",
					"Without a doubt",
					"Yes, definitely",
					"You may rely on it",
					"As I see it, yes",
					"Most likely",
					"Outlook good",
					"Yes",
					"Signs point to yes",
					"Reply hazy try again",
					"Ask again later",
					"Better not tell you now",
					"Cannot predict now",
					"Concentrate and ask again",
					"Don't count on it",
					"My reply is no",
					"My sources say no",
					"Outlook not so good",
					"Very doubtful"
			};
	
	public EightBallCommand(CommandManager manager)
	{
		super(manager);
		setDescription("Test your luck!");
		setUsage("8ball (question)");
	}
	
	@Override
	public void execute(MessageReceivedEvent event, String[] args)
	{
		if (args.length == 0)
		{
			event.getChannel().sendMessage(String.format("%s ➤ At ask 8-ball something. `=[`", event.getAuthor().getAsMention()));
			return;
		}
		event.getChannel().sendMessage(String.format("%s ➤ `%s`.", event.getAuthor().getAsMention(), responses[random.nextInt(responses.length)]));
	}
}
