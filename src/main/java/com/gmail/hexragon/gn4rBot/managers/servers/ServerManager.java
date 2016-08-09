package com.gmail.hexragon.gn4rBot.managers.servers;

import com.gmail.hexragon.gn4rBot.managers.guildMessage.GuildManager;
import com.gmail.hexragon.gn4rBot.managers.directMessage.PMManager;
import com.gmail.hexragon.gn4rBot.util.MediaCache;
import net.dv8tion.jda.entities.Guild;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * Handles multiinstances of Guilds.
 */
public class ServerManager
{
	private final MediaCache mediaCache;
	
	private final Map<String, GnarGuild> serverMap;
	
	private final PMManager privateGuild = new PMManager("DM", this);

	public ServerManager()
	{
		this.serverMap = new HashMap<>();
		this.mediaCache = new MediaCache();
	}

	private void addServer(Guild server)
	{
		serverMap.put(server.getId(), new GuildManager(server.getId(), this, server));
	}

	public void handleMessageEvent(MessageReceivedEvent event)
	{
		if (event.getAuthor().isBot())
		{
			return;
		}
		if (event.isPrivate())
		{
			//event.getMessage().getChannel().sendMessage("**" + GnarQuotes.getRandomQuote() + "** I only respond on a server. :cry:");
			privateGuild.handleMessageEvent(event);
			return;
		}

		if (!serverMap.containsKey(event.getGuild().getId())) addServer(event.getGuild());

		GnarGuild server = serverMap.get(event.getGuild().getId());

		server.handleMessageEvent(event);
	}

	public GnarGuild getGnarGuildByID(String accessID)
	{
		return serverMap.get(accessID);
	}
	
	public List<GnarGuild> getGnarGuilds()
	{
		return new ArrayList<>(serverMap.values());
	}
	
	public MediaCache getMediaCache()
	{
		return mediaCache;
	}
}
