package com.gmail.hexragon.gn4rBot.command.music;

import com.gmail.hexragon.gn4rBot.command.misc.GnarQuotes;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandManager;
import net.dv8tion.jda.entities.Message;
import net.dv8tion.jda.entities.VoiceChannel;
import org.apache.commons.lang3.StringUtils;

public class JoinChannelCommand extends MusicCommandExecutor
{
	public JoinChannelCommand(CommandManager commandManager)
	{
		setUsage("joinchannel (voice channel)");
		setDescription("Joins a voice channel.");
	}
	
	@Override
	public void execute(Message message, String[] args)
	{
		super.execute(message, args);
		
		String targetChannel = StringUtils.join(args, " ");
		
		VoiceChannel channel = getGnarGuild().getGuild().getVoiceChannels().stream()
				.filter(vChan -> vChan.getName().equalsIgnoreCase(targetChannel)).findFirst().orElse(null);
		
		if (channel == null)
		{
			message.getChannel().sendMessage(String.format("%s ➤ There's not a voice channel named `%s`. :cry:", message.getAuthor().getAsMention(), targetChannel));
			return;
		}
		
		manager.openAudioConnection(channel);
		message.getChannel().sendMessage(String.format("%s ➤ **%s** Joining the voice channel `%s`.", message.getAuthor().getAsMention(), GnarQuotes.getRandomQuote(), channel.getName()));
		
	}
	
}
