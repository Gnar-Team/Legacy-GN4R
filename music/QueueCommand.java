package com.gmail.hexragon.gn4rBot.command.music;

import com.gmail.hexragon.gn4rBot.command.misc.GnarQuotes;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandManager;
import com.gmail.hexragon.gn4rBot.util.Utils;
import net.dv8tion.jda.entities.Message;
import net.dv8tion.jda.player.MusicPlayer;
import net.dv8tion.jda.player.Playlist;
import net.dv8tion.jda.player.source.AudioInfo;
import net.dv8tion.jda.player.source.AudioSource;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.StringJoiner;

public class QueueCommand extends MusicCommandExecutor
{
	private static final int DURATION_LIMIT = 600;
	private static final int QUEUE_LIMIT = 20;
	
	public QueueCommand(CommandManager commandManager)
	{
		
		setUsage("queue [youtube url]");
		setDescription("Add music or show the current queue.");
	}
	
	@Override
	public void execute(Message message, String[] args)
	{
		super.execute(message, args);
		
		
		//show the queue
		if (args.length == 0)
		{
			StringJoiner joiner = new StringJoiner("\n");
			
			message.getChannel().sendMessage("**" + GnarQuotes.getRandomQuote() + "** Here's what's queued to be played!");
			joiner.add("```xl");
			
			if (player.getAudioQueue().isEmpty()) joiner.add(" Empty.");
			
			final int[] i = {1};
			player.getAudioQueue().stream()
					.map(AudioSource::getInfo)
					.forEach(audioInfo ->
					{
						joiner.add("[" + i[0] + "] " + audioInfo.getTitle() + "");
						i[0]++;
					});
			
			joiner.add("```");
			
			message.getChannel().sendMessage(joiner.toString());
			return;
		}
		
		// add music
		try
		{
			if (player.getAudioQueue().size() >= QUEUE_LIMIT)
			{
				message.getChannel().sendMessage(String.format("%s ➤ The music queue can only hold %d songs.", message.getAuthor().getAsMention(), QUEUE_LIMIT));
				return;
			}
			
			
			String url = args[0];
			
			//check if it is url
			try
			{
				new URL(url);
			}
			catch (MalformedURLException e) // args[0] not a url, try youtube search
			{
				String query = StringUtils.join(args, "+");
				
				String jsonURL = "https://www.googleapis.com/youtube/v3/search?part=snippet&maxResults=1&type=video&q=" + query + "&key=AIzaSyCSLbKyledFh7iRDH3jPzk-C92gXgMN5H4";
				
				JSONObject jsonObject = Utils.readJsonFromUrl(jsonURL);
				
				try
				{
					String videoID = jsonObject.getJSONArray("items").getJSONObject(0).getJSONObject("id").getString("videoId");
					url = "https://www.youtube.com/watch?v=" + videoID;
				}
				catch (Exception e1)
				{
					message.getChannel().sendMessage(String.format("%s ➤ Failed to find any video with the query `%s`.", message.getAuthor().getAsMention(), query));
					return;
				}
			}
			
			//handle trying to add the url
			Playlist playlist = Playlist.getPlaylist(url);
			List<AudioSource> sources = new LinkedList<>(playlist.getSources());
			
			if (sources.size() > 1)
			{
				message.getChannel().sendMessage(String.format("%s ➤ I've found a playlist with **%d** entries, will begin to gather info and queue sources. **(This may take a while.)**", message.getAuthor().getAsMention(), sources.size()));
				final MusicPlayer fPlayer = player;
				Thread thread = new Thread()
				{
					@Override
					public void run()
					{
						for (Iterator<AudioSource> it = sources.iterator(); it.hasNext(); )
						{
							AudioSource source = it.next();
							AudioInfo info = source.getInfo();
							List<AudioSource> queue = fPlayer.getAudioQueue();
							if (info.getError() == null)
							{
								if (source.getInfo().getDuration().getTotalSeconds() > DURATION_LIMIT)
								{
									message.getChannel().sendMessage(String.format("%s ➤ Skipping the source `%s` as it exceeded the time limit.", message.getAuthor().getAsMention(), source.getInfo().getTitle()));
									continue;
								}
								queue.add(source);
							}
							else
							{
								message.getChannel().sendMessage(String.format("%s ➤ Skipping the source due to error.\nError: %s", message.getAuthor().getAsMention(), info.getError()));
								it.remove();
							}
						}
						message.getChannel().sendMessage(String.format("%s ➤ Finished queuing provided playlist. Successfully queued **%d** sources.", message.getAuthor().getAsMention(), sources.size()));
					}
				};
				thread.start();
			}
			else
			{
				AudioSource source = sources.get(0);
				AudioInfo info = source.getInfo();
				
				if (info.getError() == null)
				{
					if (source.getInfo().getDuration().getTotalSeconds() > DURATION_LIMIT)
					{
						message.getChannel().sendMessage(String.format("%s ➤ The source `%s` exceeded the time limit.", message.getAuthor().getAsMention(), source.getInfo().getTitle()));
						return;
					}
					player.getAudioQueue().add(source);
					message.getChannel().sendMessage(String.format("%s ➤ **%s** The song `%s` has been successfully added to the queue.", message.getAuthor().getAsMention(), GnarQuotes.getRandomQuote(), source.getInfo().getTitle()));
				}
				else
				{
					message.getChannel().sendMessage(String.format("%s ➤ There was an error while loading the provided URL.\nError: %s", message.getAuthor().getAsMention(), info.getError()));
				}
			}
		}
		catch (Exception e)
		{
			message.getChannel().sendMessage(String.format("%s ➤ There was an error while loading the provided URL.", message.getAuthor().getAsMention()));
		}
	}
}
