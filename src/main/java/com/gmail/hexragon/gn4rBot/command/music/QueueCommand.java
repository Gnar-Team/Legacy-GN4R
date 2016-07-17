package com.gmail.hexragon.gn4rBot.command.music;

import com.gmail.hexragon.gn4rBot.managers.commands.CommandManager;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import net.dv8tion.jda.player.MusicPlayer;
import net.dv8tion.jda.player.Playlist;
import net.dv8tion.jda.player.source.AudioInfo;
import net.dv8tion.jda.player.source.AudioSource;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class QueueCommand extends MusicCommandExecutor
{
	public QueueCommand(CommandManager commandManager)
	{
		super(commandManager);
		setUsage("queue (video url)");
		setDescription("Add linked music to current playlist.");
	}
	
	@Override
	public void execute(MessageReceivedEvent event, String[] args)
	{
		super.execute(event, args);
		
		String msg = "";
		String url = args[0];
		Playlist playlist = Playlist.getPlaylist(url);
		List<AudioSource> sources = new LinkedList<>(playlist.getSources());
		
		if (sources.size() > 1)
		{
			event.getChannel().sendMessage("Found a playlist with **" + sources.size() + "** entries.\n" +
					"Proceeding to gather information and queue sources. This may take some time...");
			final MusicPlayer fPlayer = player;
			Thread thread = new Thread()
			{
				@Override
				public void run()
				{
					for (Iterator<AudioSource> it = sources.iterator(); it.hasNext();)
					{
						AudioSource source = it.next();
						AudioInfo info = source.getInfo();
						List<AudioSource> queue = fPlayer.getAudioQueue();
						if (info.getError() == null)
						{
							queue.add(source);
						}
						else
						{
							event.getChannel().sendMessage("Error detected, skipping source. Error:\n" + info.getError());
							it.remove();
						}
					}
					event.getChannel().sendMessage("Finished queuing provided playlist. Successfully queued **" + sources.size() + "** sources");
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
				player.getAudioQueue().add(source);
				msg += "The provided URL has been added the to queue";
				event.getChannel().sendMessage(msg + ".");
			}
			else
			{
				event.getChannel().sendMessage("There was an error while loading the provided URL.\n" +
						"Error: " + info.getError());
			}
		}
	}
}
