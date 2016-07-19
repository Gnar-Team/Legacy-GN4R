package com.gmail.hexragon.gn4rBot.command.music;

import com.gmail.hexragon.gn4rBot.command.misc.GnarQuotes;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandManager;
import com.gmail.hexragon.gn4rBot.managers.users.PermissionLevel;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

public class SkipCommand extends MusicCommandExecutor
{
	public SkipCommand(CommandManager commandManager)
	{
		super(commandManager);
		setDescription("Skip current track.");
		setPermission(PermissionLevel.BOT_COMMANDER);
	}
	
	@Override
	public void execute(MessageReceivedEvent event, String[] args)
	{
		super.execute(event, args);
		
		player.skipToNext();
		event.getChannel().sendMessage(String.format("%s ➤ **%s** Skipped the current song!", event.getAuthor().getAsMention(), GnarQuotes.getRandomQuote()));
	}
	
}
