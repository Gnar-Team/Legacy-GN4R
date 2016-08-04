package com.gmail.hexragon.gn4rBot.command.music;

import com.gmail.hexragon.gn4rBot.command.misc.GnarQuotes;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandManager;
import net.dv8tion.jda.entities.Message;

public class PlayCommand extends MusicCommandExecutor
{
	public PlayCommand(CommandManager commandManager)
	{
		setDescription("Play music or resume playback.");
	}
	
	@Override
	public void execute(Message message, String[] args)
	{
		super.execute(message, args);
		
		if (manager.getConnectedChannel() == null)
		{
			message.getChannel().sendMessage(String.format("%s ➤ I'm not in a voice channel. :cry:", message.getAuthor().getAsMention()));
			return;
		}
		
		if (player.isPlaying())
		{
			message.getChannel().sendMessage(String.format("%s ➤ **%s** I'm currently dropping the beats!", message.getAuthor().getAsMention(), GnarQuotes.getRandomQuote()));
		}
		else if (player.isPaused())
		{
			player.play();
			message.getChannel().sendMessage(String.format("%s ➤ **%s** **Resuming** the jam!", message.getAuthor().getAsMention(), GnarQuotes.getRandomQuote()));
		}
		else
		{
			if (player.getAudioQueue().isEmpty())
				message.getChannel().sendMessage(String.format("%s ➤ Theres nothing in the queue! :cry:", message.getAuthor().getAsMention()));
			else
			{
				player.play();
				message.getChannel().sendMessage(String.format("%s ➤ **%s** Turn it up! Music playback has **started**.", message.getAuthor().getAsMention(), GnarQuotes.getRandomQuote()));
			}
		}
	}
	
}
