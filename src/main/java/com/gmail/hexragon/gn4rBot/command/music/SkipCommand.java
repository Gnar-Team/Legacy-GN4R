package com.gmail.hexragon.gn4rBot.command.music;

import com.gmail.hexragon.gn4rBot.managers.commands.CommandManager;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

public class SkipCommand extends MusicCommandExecutor
{
	public SkipCommand(CommandManager commandManager)
	{
		super(commandManager);
		setDescription("Skip current track.");
	}
	
	@Override
	public void execute(MessageReceivedEvent event, String[] args)
	{
		super.execute(event, args);
		
		player.skipToNext();
		event.getChannel().sendMessage("Skipped the current song.");
	}
	
}
