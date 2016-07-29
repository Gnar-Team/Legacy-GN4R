package com.gmail.hexragon.gn4rBot.command.media;

import com.gmail.hexragon.gn4rBot.managers.commands.Command;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.util.Utils;
import net.dv8tion.jda.entities.Message;
import org.json.JSONObject;

import java.util.Random;

@Command(
		aliases = "xkcd",
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
				int max = (int) latestJSON.get("num");
				
				int rand = min + new Random().nextInt(max - min);
				
				JSONObject randJSON = Utils.readJsonFromUrl(String.format("http://xkcd.com/%d/info.0.json", rand));
				
				if (randJSON != null)
				{
					String builder =
							"XKCD **" + randJSON.get("title") + "**\n" +
									"No: **" + randJSON.get("num") + "**\n" +
									"Link: " + ((String) randJSON.get("img")).replaceAll("\\\\/", "/");
					
					message.getChannel().sendMessage(builder);
					
					return;
				}
			}
			
			message.getChannel().sendMessage(String.format("%s ➤ Unable to grab xkcd comic.", message.getAuthor().getAsMention()));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
