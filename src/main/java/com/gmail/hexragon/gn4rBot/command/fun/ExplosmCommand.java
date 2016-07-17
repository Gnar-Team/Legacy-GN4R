package com.gmail.hexragon.gn4rBot.command.fun;

import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandManager;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.Random;


public class ExplosmCommand extends CommandExecutor
{
	public ExplosmCommand(CommandManager manager)
	{
		super(manager);
		setDescription("We all need some satire.");
	}

	@Override
	public void execute(MessageReceivedEvent event, String[] args)
	{
		try
		{
			Document document;

			int min = 1500;
			int max = 4300;

			int rand = min + new Random().nextInt(max - min);

			document = Jsoup.connect(String.format("http://explosm.net/comics/%d/", rand)).get();

			StringBuilder builder = new StringBuilder();

			builder.append("Cyanide and Happiness").append("\n");
			builder.append("No: **").append(rand).append("**\n");
			builder.append("Link: ").append(document.getElementById("main-comic").absUrl("src"));

			event.getChannel().sendMessage(builder.toString());

		}
		catch (Exception e)
		{
			event.getChannel().sendMessage(String.format("%s ➤ Unable to grab Cyanide and Happiness comic.", event.getAuthor().getAsMention()));
			e.printStackTrace();
		}
	}
}
