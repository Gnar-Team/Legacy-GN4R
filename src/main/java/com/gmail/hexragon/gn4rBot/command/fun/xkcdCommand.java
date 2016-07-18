package com.gmail.hexragon.gn4rBot.command.fun;

import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandManager;
import com.gmail.hexragon.gn4rBot.util.Utils;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import org.json.JSONObject;

import java.util.Random;

public class xkcdCommand extends CommandExecutor
{
	public xkcdCommand(CommandManager manager)
	{
		super(manager);
		setDescription("Hey kid, want some programmer humor?");
	}

	@Override
	public void execute(MessageReceivedEvent event, String[] args)
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
					
					event.getChannel().sendMessage(builder);
					
					return;
				}
			}

			event.getChannel().sendMessage(String.format("%s ➤ Unable to grab xkcd comic.", event.getAuthor().getAsMention()));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
