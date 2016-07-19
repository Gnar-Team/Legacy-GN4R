package com.gmail.hexragon.gn4rBot.command.music;

import com.gmail.hexragon.gn4rBot.command.misc.GnarQuotes;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandManager;
import com.gmail.hexragon.gn4rBot.managers.users.PermissionLevel;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

public class RepeatCommand extends MusicCommandExecutor
{
	public RepeatCommand(CommandManager commandManager)
	{
		super(commandManager);
		setDescription("Toggle the ability for the queue to repeat.");
		setPermission(PermissionLevel.BOT_COMMANDER);
	}
	
	@Override
	public void execute(MessageReceivedEvent event, String[] args)
	{
		super.execute(event, args);
		
		if (!player.isRepeat())
		{
			player.setRepeat(true);
			event.getChannel().sendMessage(String.format("%s ➤ **%s** Queue repeat has been turned on.", event.getAuthor().getAsMention(), GnarQuotes.getRandomQuote()));
		}
		else
		{
			player.setRepeat(false);
			event.getChannel().sendMessage(String.format("%s ➤ **%s** Queue repeat has been turned off.", event.getAuthor().getAsMention(), GnarQuotes.getRandomQuote()));
		}
		
	}
	
}
