package com.gmail.hexragon.gn4rBot.command.music;

import com.gmail.hexragon.gn4rBot.command.misc.GnarQuotes;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandManager;
import com.gmail.hexragon.gn4rBot.managers.users.PermissionLevel;
import net.dv8tion.jda.entities.Message;

public class SkipCommand extends MusicCommandExecutor
{
	public SkipCommand(CommandManager commandManager)
	{
		
		setDescription("Skip current track.");
		setPermission(PermissionLevel.BOT_COMMANDER);
	}
	
	@Override
	public void execute(Message message, String[] args)
	{
		super.execute(message, args);
		
		player.skipToNext();
		message.getChannel().sendMessage(String.format("%s ➤ **%s** Skipped the current song!", message.getAuthor().getAsMention(), GnarQuotes.getRandomQuote()));
	}
	
}
