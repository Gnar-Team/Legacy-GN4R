package com.gmail.hexragon.gn4rBot.command.music;

import com.gmail.hexragon.gn4rBot.command.misc.GnarQuotes;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandManager;
import net.dv8tion.jda.entities.VoiceChannel;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import org.apache.commons.lang3.StringUtils;

public class JoinChannelCommand extends MusicCommandExecutor
{
	public JoinChannelCommand(CommandManager commandManager)
	{
		super(commandManager);
		setUsage("joinchannel (voice channel)");
		setDescription("Joins a voice channel.");
	}
	
	@Override
	public void execute(MessageReceivedEvent event, String[] args)
	{
		super.execute(event, args);
		
		String targetChannel = StringUtils.join(args, " ");
		
		VoiceChannel channel = event.getGuild().getVoiceChannels().stream()
				.filter(vChan -> vChan.getName().equalsIgnoreCase(targetChannel)).findFirst().orElse(null);
		
		if (channel == null)
		{
			event.getChannel().sendMessage(String.format("%s ➤ There's not a voice channel named `%s`. :cry:", event.getAuthor().getAsMention(), targetChannel));
			return;
		}
		
		manager.openAudioConnection(channel);
		event.getChannel().sendMessage(String.format("%s ➤ **%s** Joining the voice channel `%s`.", event.getAuthor().getAsMention(), GnarQuotes.getRandomQuote(), channel.getName()));
		
	}
	
}
