package com.gmail.hexragon.gn4rBot.command.music;

import com.gmail.hexragon.gn4rBot.command.misc.GnarQuotes;
import net.dv8tion.jda.entities.Message;

public class RestartCommand extends MusicCommandExecutor
{
	@Override
	public void execute(Message message, String[] args)
	{
		super.execute(message, args);
		
		if (player.isStopped())
		{
			if (player.getPreviousAudioSource() != null)
			{
				player.reload(true);
				message.getChannel().sendMessage(String.format("%s ➤ %s Restarting previous song!", message.getAuthor().getAsMention(), GnarQuotes.getRandomQuote()));
			}
			else
			{
				message.getChannel().sendMessage(String.format("%s ➤ Can't restart a song if I never played one. :cry:", message.getAuthor().getAsMention()));
			}
		}
		else
		{
			player.reload(true);
			message.getChannel().sendMessage(String.format("%s ➤ %s Restarting current song!", message.getAuthor().getAsMention(), GnarQuotes.getRandomQuote()));
		}
	}
}
