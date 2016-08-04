package com.gmail.hexragon.gn4rBot.command.music;

import com.gmail.hexragon.gn4rBot.command.misc.GnarQuotes;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandManager;
import com.gmail.hexragon.gn4rBot.managers.users.PermissionLevel;
import net.dv8tion.jda.entities.Message;

public class PauseCommand extends MusicCommandExecutor
{
	public PauseCommand(CommandManager commandManager)
	{
		
		setDescription("Play music or resume playback.");
		setPermission(PermissionLevel.BOT_COMMANDER);
	}
	
	@Override
	public void execute(Message message, String[] args)
	{
		super.execute(message, args);
		
		if (player.isPaused())
		{
			message.getChannel().sendMessage(String.format("%s ➤ **%s** I'm already on pause.", message.getAuthor().getAsMention(), GnarQuotes.getRandomQuote()));
		}
		else
		{
			player.pause();
			message.getChannel().sendMessage(String.format("%s ➤ **%s** Alright, let's take a lil' break!", message.getAuthor().getAsMention(), GnarQuotes.getRandomQuote()));
		}
	}
	
}
