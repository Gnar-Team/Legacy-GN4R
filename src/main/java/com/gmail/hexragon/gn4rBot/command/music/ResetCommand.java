package com.gmail.hexragon.gn4rBot.command.music;

import com.gmail.hexragon.gn4rBot.command.misc.GnarQuotes;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandManager;
import com.gmail.hexragon.gn4rBot.managers.users.PermissionLevel;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import net.dv8tion.jda.player.MusicPlayer;

import static net.dv8tion.jda.player.Bot.DEFAULT_VOLUME;

public class ResetCommand extends MusicCommandExecutor
{
	public ResetCommand(CommandManager commandManager)
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
		player = new MusicPlayer();
		player.setVolume(DEFAULT_VOLUME);
		manager.setSendingHandler(player);
		event.getChannel().sendMessage(String.format("%s ➤ %s Music player has been reset.", event.getAuthor().getAsMention(), GnarQuotes.getRandomQuote()));
	}
	
}
