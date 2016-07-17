package com.gmail.hexragon.gn4rBot.command.music;

import com.gmail.hexragon.gn4rBot.managers.commands.CommandManager;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

public class StopCommand extends MusicCommandExecutor
{
	public StopCommand(CommandManager commandManager)
	{
		super(commandManager);
		setDescription("Stop music playback.");
	}
	
	@Override
	public void execute(MessageReceivedEvent event, String[] args)
	{
		super.execute(event, args);
		
		manager.closeAudioConnection();
	}
	
}
