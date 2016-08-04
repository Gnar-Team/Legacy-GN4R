package com.gmail.hexragon.gn4rBot.command.music;

import com.gmail.hexragon.gn4rBot.command.misc.GnarQuotes;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandManager;
import com.gmail.hexragon.gn4rBot.managers.users.PermissionLevel;
import net.dv8tion.jda.entities.Message;
import net.dv8tion.jda.player.MusicPlayer;

import static net.dv8tion.jda.player.Bot.DEFAULT_VOLUME;

public class ResetCommand extends MusicCommandExecutor
{
	public ResetCommand(CommandManager commandManager)
	{
		
		setDescription("Stop music playback.");
		setPermission(PermissionLevel.BOT_COMMANDER);
	}
	
	@Override
	public void execute(Message message, String[] args)
	{
		super.execute(message, args);
		
		player.stop();
		player = new MusicPlayer();
		player.setVolume(DEFAULT_VOLUME);
		manager.setSendingHandler(player);
		message.getChannel().sendMessage(String.format("%s ➤ %s Music player has been reset.", message.getAuthor().getAsMention(), GnarQuotes.getRandomQuote()));
	}
	
}
