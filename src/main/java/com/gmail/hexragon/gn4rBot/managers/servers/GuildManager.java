package com.gmail.hexragon.gn4rBot.managers.servers;

import net.dv8tion.jda.entities.Guild;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

import java.util.LinkedHashMap;
import java.util.Map;

/*
 * Handles multiinstances of Guilds.
 */
public class GuildManager
{
	private static GuildManager instance;

	private Map<String, GnarGuild> serverMap;

	public GuildManager()
	{
		instance = this;

		this.serverMap = new LinkedHashMap<>();
	}

	public static GuildManager getInstance()
	{
		return instance;
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

	public GnarGuild getServer(String accessID)
	{
		return serverMap.get(accessID);
	}
}
