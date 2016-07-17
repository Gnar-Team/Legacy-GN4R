package com.gmail.hexragon.gn4rBot.command.music;

import com.gmail.hexragon.gn4rBot.managers.commands.CommandManager;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import net.dv8tion.jda.player.source.AudioSource;

import java.util.StringJoiner;

public class ShowQueueCommand extends MusicCommandExecutor
{
	public ShowQueueCommand(CommandManager commandManager)
	{
		super(commandManager);
		setDescription("Leave current voice channel.");
	}
	
	@Override
	public void execute(MessageReceivedEvent event, String[] args)
	{
		super.execute(event, args);
		
		StringJoiner joiner = new StringJoiner("\n");
		
		joiner.add("**Current Queue:**");
		joiner.add("```xl");
		
		if (player.getAudioQueue().isEmpty()) joiner.add(" Empty.");
		
		final int[] i = {1};
		player.getAudioQueue().stream()
				.map(AudioSource::getInfo)
				.forEach(audioInfo ->
				{
					joiner.add("["+i[0]+ "] "+audioInfo.getTitle()+"");
					i[0]++;
				});
		
		joiner.add("```");
		
		event.getChannel().sendMessage(joiner.toString());
	}
	
}
