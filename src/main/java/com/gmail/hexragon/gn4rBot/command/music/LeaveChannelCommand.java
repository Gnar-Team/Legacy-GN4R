package com.gmail.hexragon.gn4rBot.command.music;

import com.gmail.hexragon.gn4rBot.command.misc.GnarQuotes;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandManager;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

public class LeaveChannelCommand extends MusicCommandExecutor
{
	public LeaveChannelCommand(CommandManager commandManager)
	{
		super(commandManager);
		setDescription("Leave current voice channel.");
	}
	
	@Override
	public void execute(MessageReceivedEvent event, String[] args)
	{
		super.execute(event, args);
		
		manager.closeAudioConnection();
		
		event.getChannel().sendMessage(String.format("%s ➤ **%s** Leaving the voice channel.", event.getAuthor().getAsMention(), GnarQuotes.getRandomQuote()));
	}
	
}
