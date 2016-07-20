package com.gmail.hexragon.gn4rBot.command.music;

import com.gmail.hexragon.gn4rBot.command.misc.GnarQuotes;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandManager;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import net.dv8tion.jda.player.MusicPlayer;
import net.dv8tion.jda.player.Playlist;
import net.dv8tion.jda.player.source.AudioInfo;
import net.dv8tion.jda.player.source.AudioSource;

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
		super(commandManager);
		setUsage("queue [youtube url]");
		setDescription("Add music or show the current queue.");
	}
	
	@Override
	public void execute(MessageReceivedEvent event, String[] args)
	{
		super.execute(event, args);
		
		if (args.length == 0)
		{
			StringJoiner joiner = new StringJoiner("\n");
			
			event.getChannel().sendMessage("**" + GnarQuotes.getRandomQuote() + "** Here's what's queued to be played!");
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
			
			event.getChannel().sendMessage(joiner.toString());
			return;
		}
		
		try
		{
			if (player.getAudioQueue().size() >= QUEUE_LIMIT)
			{
				event.getChannel().sendMessage(String.format("%s ➤ The music queue can only hold %d songs.", event.getAuthor().getAsMention(), QUEUE_LIMIT));
				return;
			}
			
			String url = args[0];
			Playlist playlist = Playlist.getPlaylist(url);
			List<AudioSource> sources = new LinkedList<>(playlist.getSources());
			
			if (sources.size() > 1)
			{
				event.getChannel().sendMessage(String.format("%s ➤ I've found a playlist with **%d** entries, will begin to gather info and queue sources. **(This may take a while.)**", event.getAuthor().getAsMention(), sources.size()));
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
									event.getChannel().sendMessage(String.format("%s ➤ Skipping the source `%s` as it exceeded the time limit.", event.getAuthor().getAsMention(), source.getInfo().getTitle()));
									continue;
								}
								queue.add(source);
							}
							else
							{
								event.getChannel().sendMessage(String.format("%s ➤ Skipping the source due to error.\nError: %s", event.getAuthor().getAsMention(), info.getError()));
								it.remove();
							}
						}
						event.getChannel().sendMessage(String.format("%s ➤ Finished queuing provided playlist. Successfully queued **%d** sources.", event.getAuthor().getAsMention(), sources.size()));
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
						event.getChannel().sendMessage(String.format("%s ➤ The source `%s` exceeded the time limit.", event.getAuthor().getAsMention(), source.getInfo().getTitle()));
						return;
					}
					player.getAudioQueue().add(source);
					event.getChannel().sendMessage(String.format("%s ➤ **%s** The song '%s' has been successfully added to the queue.", event.getAuthor().getAsMention(), GnarQuotes.getRandomQuote(), source.getInfo().getTitle()));
				}
				else
				{
					event.getChannel().sendMessage(String.format("%s ➤ There was an error while loading the provided URL.\nError: %s", event.getAuthor().getAsMention(), info.getError()));
				}
			}
		}
		catch (Exception e)
		{
			event.getChannel().sendMessage(String.format("%s ➤ There was an error while loading the provided URL.", event.getAuthor().getAsMention()));
		}
	}
}
