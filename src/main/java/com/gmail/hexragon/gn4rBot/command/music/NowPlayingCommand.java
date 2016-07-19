package com.gmail.hexragon.gn4rBot.command.music;

import com.gmail.hexragon.gn4rBot.command.misc.GnarQuotes;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandManager;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import net.dv8tion.jda.player.source.AudioInfo;
import net.dv8tion.jda.player.source.AudioTimestamp;

public class NowPlayingCommand extends MusicCommandExecutor
{
	public NowPlayingCommand(CommandManager commandManager)
	{
		super(commandManager);
		setDescription("Show info on currently playing music.");
	}
	
	@Override
	public void execute(MessageReceivedEvent event, String[] args)
	{
		super.execute(event, args);
		
		if (player.isPlaying())
		{
			AudioTimestamp currentTime = player.getCurrentTimestamp();
			AudioInfo info = player.getCurrentAudioSource().getInfo();
			
			event.getChannel().sendMessage(String.format("%s ➤ **%s** Here's some info on what's currently played.", event.getAuthor().getAsMention(), GnarQuotes.getRandomQuote()));
			
			if (info.getError() == null)
			{
				event.getChannel().sendMessage(
						"```\nPlaying:  " + info.getTitle() + "\n" +
							 "Time:     [" + currentTime.getTimestamp() + " / " + info.getDuration().getTimestamp() + "]\n```");
			}
			else
			{
				event.getChannel().sendMessage(
						"*```\nPlaying: Info Error. Known source: " + player.getCurrentAudioSource().getSource() + "\n" +
							  "Time:    [" + currentTime.getTimestamp() + " / (N/A)]\n```");
			}
		}
		else
		{
			event.getChannel().sendMessage("The player is not currently playing anything!");
		}
	}
	
}
