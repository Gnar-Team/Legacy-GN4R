package com.gmail.hexragon.gn4rBot.command.music;

import com.gmail.hexragon.gn4rBot.managers.commands.CommandManager;
import com.gmail.hexragon.gn4rBot.managers.users.PermissionLevel;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

public class StopCommand extends MusicCommandExecutor
{
	public StopCommand(CommandManager commandManager)
	{
		super(commandManager);
		setDescription("Stop music playback.");
		setPermission(PermissionLevel.BOT_COMMANDER);
	}
	
	@Override
	public void execute(MessageReceivedEvent event, String[] args)
	{
		super.execute(event, args);
		
		player.stop();
		event.getChannel().sendMessage(String.format("%s ➤ Playback has been stopped. :cry:", event.getAuthor().getAsMention()));
	}
	
}
