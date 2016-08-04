package com.gmail.hexragon.gn4rBot.command.music;

import com.gmail.hexragon.gn4rBot.command.misc.GnarQuotes;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandManager;
import com.gmail.hexragon.gn4rBot.managers.users.PermissionLevel;
import net.dv8tion.jda.entities.Message;

public class RepeatCommand extends MusicCommandExecutor
{
	public RepeatCommand(CommandManager commandManager)
	{
		
		setDescription("Toggle the ability for the queue to repeat.");
		setPermission(PermissionLevel.BOT_COMMANDER);
	}
	
	@Override
	public void execute(Message message, String[] args)
	{
		super.execute(message, args);
		
		if (!player.isRepeat())
		{
			player.setRepeat(true);
			message.getChannel().sendMessage(String.format("%s ➤ **%s** Queue repeat has been turned on.", message.getAuthor().getAsMention(), GnarQuotes.getRandomQuote()));
		}
		else
		{
			player.setRepeat(false);
			message.getChannel().sendMessage(String.format("%s ➤ **%s** Queue repeat has been turned off.", message.getAuthor().getAsMention(), GnarQuotes.getRandomQuote()));
		}
		
	}
	
}
