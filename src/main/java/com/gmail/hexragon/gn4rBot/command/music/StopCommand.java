package com.gmail.hexragon.gn4rBot.command.music;

import com.gmail.hexragon.gn4rBot.managers.commands.CommandManager;
import com.gmail.hexragon.gn4rBot.managers.users.PermissionLevel;
import net.dv8tion.jda.entities.Message;

public class StopCommand extends MusicCommandExecutor
{
	public StopCommand(CommandManager commandManager)
	{
		
		setDescription("Stop music playback.");
		setPermission(PermissionLevel.BOT_COMMANDER);
	}
	
	@Override
	public void execute(Message message, String[] args)
	{
		super.execute(message, args);
		
		player.stop();
		message.getChannel().sendMessage(String.format("%s ➤ Playback has been stopped. :cry:", message.getAuthor().getAsMention()));
	}
	
}
