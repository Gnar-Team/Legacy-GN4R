package com.gmail.hexragon.gn4rBot.command.music;

import com.gmail.hexragon.gn4rBot.managers.commands.CommandManager;
import com.gmail.hexragon.gn4rBot.managers.users.PermissionLevel;
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
		setPermission(PermissionLevel.BOT_COMMANDER);
	}
	
	@Override
	public void execute(MessageReceivedEvent event, String[] args)
	{
		super.execute(event, args);
		
		//Separates the name of the channel so that we can search for it
		String chanName = StringUtils.join(args, " ");
		
		//Scans through the VoiceChannels in this Guild, looking for one with a case-insensitive matching name.
		VoiceChannel channel = event.getGuild().getVoiceChannels().stream()
				.filter(vChan -> vChan.getName().equalsIgnoreCase(chanName))
				.findFirst()
				.orElse(null);  //If there isn't a matching name, return null.
		if (channel == null)
		{
			event.getChannel().sendMessage("There isn't a VoiceChannel in this Guild with the name: '" + chanName + "'");
			return;
		}
		manager.openAudioConnection(channel);
		
	}
	
}
