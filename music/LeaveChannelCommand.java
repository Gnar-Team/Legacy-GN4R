package com.gmail.hexragon.gn4rBot.command.music;

import com.gmail.hexragon.gn4rBot.command.misc.GnarQuotes;
import net.dv8tion.jda.entities.Message;

public class LeaveChannelCommand extends MusicCommandExecutor
{
	public LeaveChannelCommand()
	{
		setDescription("Leave current voice channel.");
	}
	
	@Override
	public void execute(Message message, String[] args)
	{
		super.execute(message, args);
		
		manager.closeAudioConnection();
		
		message.getChannel().sendMessage(String.format("%s ➤ **%s** Leaving the voice channel.", message.getAuthor().getAsMention(), GnarQuotes.getRandomQuote()));
	}
	
}
