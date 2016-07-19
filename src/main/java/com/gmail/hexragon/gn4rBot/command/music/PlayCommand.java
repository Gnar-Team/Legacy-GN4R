package com.gmail.hexragon.gn4rBot.command.music;

import com.gmail.hexragon.gn4rBot.command.misc.GnarQuotes;
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
		
		if (manager.getConnectedChannel() == null)
		{
			event.getChannel().sendMessage(String.format("%s ➤ I'm not in a voice channel. :cry:", event.getAuthor().getAsMention()));
			return;
		}
		
		if (player.isPlaying())
		{
			event.getChannel().sendMessage(String.format("%s ➤ **%s** I'm currently dropping the beats!", event.getAuthor().getAsMention(), GnarQuotes.getRandomQuote()));
		}
		else if (player.isPaused())
		{
			player.play();
			event.getChannel().sendMessage(String.format("%s ➤ **%s** **Resuming** the jam!", event.getAuthor().getAsMention(), GnarQuotes.getRandomQuote()));
		}
		else
		{
			if (player.getAudioQueue().isEmpty())
				event.getChannel().sendMessage(String.format("%s ➤ Theres nothing in the queue! :cry:", event.getAuthor().getAsMention()));
			else
			{
				player.play();
				event.getChannel().sendMessage(String.format("%s ➤ **%s** Turn it up! Music playback has **started**.", event.getAuthor().getAsMention(), GnarQuotes.getRandomQuote()));
			}
		}
	}
	
}
