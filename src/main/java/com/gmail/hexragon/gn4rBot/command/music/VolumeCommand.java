package com.gmail.hexragon.gn4rBot.command.music;

import com.gmail.hexragon.gn4rBot.managers.commands.CommandManager;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

public class VolumeCommand extends MusicCommandExecutor
{
	public VolumeCommand(CommandManager commandManager)
	{
		super(commandManager);
		setDescription("Set music volume.");
	}
	
	@Override
	public void execute(MessageReceivedEvent event, String[] args)
	{
		super.execute(event, args);
		
		float volume = Float.parseFloat(args[0]);
		
		if (volume < 0) volume = 0;
		
		volume = Math.min(100, volume);
		
		player.setVolume(volume / 100f);
		event.getChannel().sendMessage("Volume was changed to: " + volume);
	}
	
}
