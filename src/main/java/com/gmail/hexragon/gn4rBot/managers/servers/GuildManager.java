package com.gmail.hexragon.gn4rBot.managers.servers;

import com.gmail.hexragon.gn4rBot.util.MediaCache;
import net.dv8tion.jda.entities.Guild;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

import java.util.LinkedHashMap;
import java.util.Map;

/*
 * Handles multiinstances of Guilds.
 */
public class GuildManager
{
	private final MediaCache mediaCache;
	
	private final Map<String, GnarGuild> serverMap;

	public GuildManager()
	{
		this.serverMap = new LinkedHashMap<>();
		this.mediaCache = new MediaCache();
	}

	public void addServer(Guild server)
	{
		GnarGuild gServer = new GnarGuild(server.getId(), this, server);
		gServer.defaultSetup();
		serverMap.put(server.getId(), gServer);
	}

	public void messageEvent(MessageReceivedEvent event)
	{
		if (event.isPrivate())
		{

			return;
		}

		if (!serverMap.containsKey(event.getGuild().getId())) addServer(event.getGuild());

		GnarGuild server = serverMap.get(event.getGuild().getId());

		server.messageEvent(event);
	}

	public GnarGuild getGnarGuildByID(String accessID)
	{
		return serverMap.get(accessID);
	}
	
	public MediaCache getMediaCache()
	{
		return mediaCache;
	}
}
