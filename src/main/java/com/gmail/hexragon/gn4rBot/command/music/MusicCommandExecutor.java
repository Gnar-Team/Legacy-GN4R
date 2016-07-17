package com.gmail.hexragon.gn4rBot.command.music;

import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandManager;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import net.dv8tion.jda.managers.AudioManager;
import net.dv8tion.jda.player.MusicPlayer;

import static net.dv8tion.jda.player.Bot.DEFAULT_VOLUME;

abstract class MusicCommandExecutor extends CommandExecutor
{
	protected AudioManager manager;
	MusicPlayer player;
	
	MusicCommandExecutor(CommandManager commandManager)
	{
		super(commandManager);
	}
	
	@Override
	public void execute(MessageReceivedEvent event, String[] args)
	{
		manager = event.getGuild().getAudioManager();
		
		if (manager.getSendingHandler() == null)
		{
			player = new MusicPlayer();
			player.setVolume(DEFAULT_VOLUME);
			manager.setSendingHandler(player);
		}
		else
		{
			player = (MusicPlayer) manager.getSendingHandler();
		}
	}
	
	@Override
	public String getDescription()
	{
		return "♬ MUSIC: "+super.getDescription();
	}
}
