package com.gmail.hexragon.gn4rBot.command.general;

import com.gmail.hexragon.gn4rBot.GnarBot;
import com.gmail.hexragon.gn4rBot.managers.commands.annotations.Command;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.util.GnarMessage;
import com.gmail.hexragon.gn4rBot.util.Utils;
import net.dv8tion.jda.entities.Message;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

@Command(
		aliases = "youtube",
		usage = "(query)",
		description = "Search and get a YouTube video."
)
public class YoutubeCommand extends CommandExecutor
{
	@Override
	public void execute(GnarMessage message, String[] args)
	{
		if (args.length == 0)
		{
			message.reply("Gotta have a query to YouTube.");
			return;
		}
		
		try
		{
			String query = StringUtils.join(args, " ");
			Message msg = message.reply("Searching `"+ query +"`.");
			
			query = query.replace(" ", "+");
			
			String url = "https://www.googleapis.com/youtube/v3/search?part=snippet&maxResults=1&type=video&q=" + query + "&key="+ GnarBot.TOKENS.get("youtube");
			
			JSONObject jsonObject = Utils.readJsonFromUrl(url);
			
			String videoID = jsonObject.getJSONArray("items").getJSONObject(0).getJSONObject("id").getString("videoId");
			message.getChannel().sendMessage("https://www.youtube.com/watch?v=" + videoID);
			msg.deleteMessage();
		}
		catch (JSONException | NullPointerException e)
		{
			message.reply("Unable to get YouTube results.");
			e.printStackTrace();
		}
	}
}



