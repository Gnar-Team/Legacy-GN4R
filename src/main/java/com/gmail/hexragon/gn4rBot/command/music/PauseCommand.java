package com.gmail.hexragon.gn4rBot.command.music;

import com.gmail.hexragon.gn4rBot.command.misc.GnarQuotes;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandManager;
import com.gmail.hexragon.gn4rBot.managers.users.PermissionLevel;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

public class PauseCommand extends MusicCommandExecutor
{
	public PauseCommand(CommandManager commandManager)
	{
		super(commandManager);
		setDescription("Play music or resume playback.");
		setPermission(PermissionLevel.BOT_COMMANDER);
	}
	
	@Override
	public void execute(MessageReceivedEvent event, String[] args)
	{
		super.execute(event, args);
		
		if (player.isPaused())
		{
			event.getChannel().sendMessage(String.format("%s ➤ **%s** I'm already on pause.", event.getAuthor().getAsMention(), GnarQuotes.getRandomQuote()));
		}
		else
		{
			player.pause();
			event.getChannel().sendMessage(String.format("%s ➤ **%s** Alright, let's take a lil' break!", event.getAuthor().getAsMention(), GnarQuotes.getRandomQuote()));
		}
	}
	
}
