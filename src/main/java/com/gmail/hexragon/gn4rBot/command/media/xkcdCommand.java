package com.gmail.hexragon.gn4rBot.command.media;

import com.gmail.hexragon.gn4rBot.managers.commands.Command;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.util.Utils;
import net.dv8tion.jda.entities.Message;
import org.json.JSONObject;

import java.util.Random;

@Command(
		aliases = "xkcd",
		usage = "[number|latest]",
		description = "Ey keed, want some programmer hum0r?."
)
public class xkcdCommand extends CommandExecutor
{
	@Override
	public void execute(Message message, String[] args)
	{
		try
		{
			JSONObject latestJSON = Utils.readJsonFromUrl("http://xkcd.com/info.0.json");
			
			if (latestJSON != null)
			{
				int min = 500;
				int max = latestJSON.getInt("num");
				
				int rand;
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
						
						rand = input;
					}
					catch (NumberFormatException e)
					{
						if (args[0].equalsIgnoreCase("latest"))
						{
							rand = max;
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
					rand = min + new Random().nextInt(max - min);
				}
				
				JSONObject randJSON = Utils.readJsonFromUrl(String.format("http://xkcd.com/%d/info.0.json", rand));
				
				if (randJSON != null)
				{
					String builder =
							"XKCD **" + randJSON.getString("title") + "**\n" +
									"No: **" + randJSON.getInt("num") + "**\n" +
									"Link: " + randJSON.getString("img").replaceAll("\\\\/", "/");
					
					message.getChannel().sendMessage(builder);
					
					return;
				}
			}
			
			message.getChannel().sendMessage(String.format("%s ➜ Unable to grab xkcd comic.", message.getAuthor().getAsMention()));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
