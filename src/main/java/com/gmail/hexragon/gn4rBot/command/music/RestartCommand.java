package com.gmail.hexragon.gn4rBot.command.music;

import com.gmail.hexragon.gn4rBot.command.misc.GnarQuotes;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandManager;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

public class RestartCommand extends MusicCommandExecutor
{
	public RestartCommand(CommandManager cm)
	{
		super(cm);
	}
	
	@Override
	public void execute(MessageReceivedEvent event, String[] args)
	{
		super.execute(event, args);
		
		if (player.isStopped())
		{
			if (player.getPreviousAudioSource() != null)
			{
				player.reload(true);
				event.getChannel().sendMessage(String.format("%s ➤ %s Restarting previous song!", event.getAuthor().getAsMention(), GnarQuotes.getRandomQuote()));
			}
			else
			{
				event.getChannel().sendMessage(String.format("%s ➤ Can't restart a song if I never played one. :cry:", event.getAuthor().getAsMention()));
			}
		}
		else
		{
			player.reload(true);
			event.getChannel().sendMessage(String.format("%s ➤ %s Restarting current song!", event.getAuthor().getAsMention(), GnarQuotes.getRandomQuote()));
		}
	}
}
