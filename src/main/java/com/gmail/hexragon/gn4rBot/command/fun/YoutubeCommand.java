package com.gmail.hexragon.gn4rBot.command.fun;

import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandManager;
import com.gmail.hexragon.gn4rBot.util.Utils;
import net.dv8tion.jda.entities.Message;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class YoutubeCommand extends CommandExecutor
{
	public YoutubeCommand(CommandManager manager)
	{
		super(manager);
		setDescription("Because who needs browsers?");
		setUsage("google (query)");
	}
	
	@Override
	public void execute(MessageReceivedEvent event, String[] args)
	{
		if (args.length == 0)
		{
			event.getChannel().sendMessage(String.format("%s ➤ Gotta have a query to YouTube.", event.getAuthor().getAsMention()));
			return;
		}
		
		try
		{
			String query = StringUtils.join(args, " ");
			Message msg = event.getChannel().sendMessage(String.format("%s ➤ Searching `%s`.", event.getAuthor().getAsMention(), query));
			
			query = query.replace(" ", "+");
			
			String url = "https://www.googleapis.com/youtube/v3/search?part=snippet&maxResults=1&type=video&q=" + query + "&key=AIzaSyCSLbKyledFh7iRDH3jPzk-C92gXgMN5H4";
			
			JSONObject jsonObject = Utils.readJsonFromUrl(url);
			
			String videoID = jsonObject.getJSONArray("items").getJSONObject(0).getJSONObject("id").getString("videoId");
			event.getChannel().sendMessage("https://www.youtube.com/watch?v=" + videoID);
			msg.deleteMessage();
			
		}
		catch (JSONException | NullPointerException e)
		{
			event.getChannel().sendMessage(String.format("%s ➤ Unable to get YouTube results.", event.getAuthor().getAsMention()));
			e.printStackTrace();
		}
	}
}



