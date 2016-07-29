package com.gmail.hexragon.gn4rBot.managers.servers;

import com.gmail.hexragon.gn4rBot.command.misc.GnarQuotes;
import com.gmail.hexragon.gn4rBot.util.MediaCache;
import net.dv8tion.jda.entities.Guild;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

import java.util.HashMap;
import java.util.Map;

/*
 * Handles multiinstances of Guilds.
 */
public class ServerManagers
{
	private final MediaCache mediaCache;
	
	private final Map<String, GnarGuild> serverMap;

	public ServerManagers()
	{
		this.serverMap = new HashMap<>();
		this.mediaCache = new MediaCache();
	}

	public void addServer(Guild server)
	{
		GnarGuild gServer = new GnarGuild(server.getId(), this, server);
		gServer.defaultSetup();
		serverMap.put(server.getId(), gServer);
	}

	public void handleMessageEvent(MessageReceivedEvent event)
	{
		if (event.isPrivate())
		{
			event.getMessage().getChannel().sendMessage("**" + GnarQuotes.getRandomQuote() + "** I only respond on a server. :cry:");
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
	
	public MediaCache getMediaCache()
	{
		return mediaCache;
	}
}
