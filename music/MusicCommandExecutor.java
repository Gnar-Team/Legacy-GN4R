package com.gmail.hexragon.gn4rBot.command.music;

import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import net.dv8tion.jda.entities.Message;
import net.dv8tion.jda.managers.AudioManager;
import net.dv8tion.jda.player.MusicPlayer;

import static net.dv8tion.jda.player.Bot.DEFAULT_VOLUME;

public abstract class MusicCommandExecutor extends CommandExecutor
{
	protected AudioManager manager;
	MusicPlayer player;
	
	@Override
	public void execute(Message message, String[] args)
	{
		manager = getGnarGuild().getGuild().getAudioManager();
		
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
}
