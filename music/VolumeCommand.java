package com.gmail.hexragon.gn4rBot.command.music;

import com.gmail.hexragon.gn4rBot.command.misc.GnarQuotes;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandManager;
import com.gmail.hexragon.gn4rBot.managers.users.PermissionLevel;
import net.dv8tion.jda.entities.Message;

public class VolumeCommand extends MusicCommandExecutor
{
	public VolumeCommand(CommandManager commandManager)
	{
		
		setDescription("Set music volume.");
		setUsage("volume (0-100)");
		setPermission(PermissionLevel.BOT_COMMANDER);
	}
	
	@Override
	public void execute(Message message, String[] args)
	{
		super.execute(message, args);
		
		try
		{
			int displayVol = Integer.parseInt(args[0]);
			float volume = displayVol;
			
			if (volume < 0) volume = 0;
			volume = Math.min(100, volume);
			
			player.setVolume(volume / 100f);
			
			message.getChannel().sendMessage(
					String.format("%s ➤ **%s** Volume has been changed to %d.",
							message.getAuthor().getAsMention(), GnarQuotes.getRandomQuote(), displayVol));
			
			StringBuilder bar = new StringBuilder("                    ");
			for (int i = 0; i < displayVol / 5; i++)
			{
				bar.setCharAt(i, '█');
			}
			
			message.getChannel().sendMessage(":sound: `[" + bar.toString() + "]`");
		}
		catch (Exception e)
		{
			message.getChannel().sendMessage(String.format("%s ➤ You didn't enter a proper number! `[0-100]` :cry:", message.getAuthor().getAsMention()));
		}
	}
	
}
