package com.gmail.hexragon.gn4rBot.command.music;

import com.gmail.hexragon.gn4rBot.managers.commands.CommandManager;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

public class PlayCommand extends MusicCommandExecutor
{
	public PlayCommand(CommandManager commandManager)
	{
		super(commandManager);
		setDescription("Play music or resume playback.");
	}
	
	@Override
	public void execute(MessageReceivedEvent event, String[] args)
	{
		super.execute(event, args);
		
		if (player.isPlaying())
		{
			event.getChannel().sendMessage("Playback is already playing.");
		}
		else if (player.isPaused())
		{
			player.play();
			event.getChannel().sendMessage("Playback as been resumed.");
		}
		else
		{
			if (player.getAudioQueue().isEmpty())
				event.getChannel().sendMessage("The current audio queue is empty. Add something to the queue first. ");
			else
			{
				player.play();
				event.getChannel().sendMessage("Playback has started playing.");
			}
		}
	}
	
}
