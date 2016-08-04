package com.gmail.hexragon.gn4rBot.command.media;

import com.gmail.hexragon.gn4rBot.managers.commands.Command;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import net.dv8tion.jda.entities.Message;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.Random;

@Command(
		aliases = {"c&h", "explosm"},
		usage = "[number|latest]",
		description = "Shit-posts from Explosm."
)
public class ExplosmCommand extends CommandExecutor
{
	public ExplosmCommand()
	{
		setDescription("We all need some satire.");
	}
	
	@Override
	public void execute(Message message, String[] args)
	{
		try
		{
			Document document;
			
			int min = 1500;
			int max = 4300;
			
			String rand;
			
			if (args.length >= 1)
			{
				int input;
				try
				{
					input = Integer.valueOf(args[0]);
					
					if (input > max || input < 0)
					{
						message.getChannel().sendMessage(String.format("%s ➜ xkcd does not have a comic for that number.", message.getAuthor().getAsMention()));
					}
					
					rand = String.valueOf(input);
				}
				catch (NumberFormatException e)
				{
					if (args[0].equalsIgnoreCase("latest"))
					{
						rand = "latest";
					}
					else
					{
						message.getChannel().sendMessage(String.format("%s ➜ You didn't enter a proper number.", message.getAuthor().getAsMention()));
						return;
					}
				}
			}
			else
			{
				rand = String.valueOf(min + new Random().nextInt(max - min));
			}
			
			document = Jsoup.connect(String.format("http://explosm.net/comics/%s/", rand)).get();
			
			String builder =
					"Cyanide and Happiness" + "\n" +
							"No: **" + rand + "**\n" +
							"Link: " + document.getElementById("main-comic").absUrl("src");
			
			message.getChannel().sendMessage(builder);
		}
		catch (Exception e)
		{
			message.getChannel().sendMessage(String.format("%s ➜ Unable to grab Cyanide and Happiness comic.", message.getAuthor().getAsMention()));
			e.printStackTrace();
		}
	}
}
