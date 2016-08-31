package com.gmail.hexragon.gn4rBot.managers.servers;

import com.gmail.hexragon.gn4rBot.managers.directMessage.PMManager;
import com.gmail.hexragon.gn4rBot.managers.guildMessage.GuildManager;
import com.gmail.hexragon.gn4rBot.util.DiscordBotsInfo;
import com.gmail.hexragon.gn4rBot.util.MediaCache;
import net.dv8tion.jda.JDA;
import net.dv8tion.jda.entities.Guild;
import net.dv8tion.jda.events.ShutdownEvent;
import net.dv8tion.jda.events.guild.GuildJoinEvent;
import net.dv8tion.jda.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import net.dv8tion.jda.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * Handles multiinstances of Guilds.
 */
public class ServerManager
{
    private final JDA jda;
    
    private final MediaCache mediaCache;
    
    private final Map<String, GnarManager> serverMap;
    
    private final PMManager privateGuild = new PMManager("DM", this);
    
    public ServerManager(JDA jda)
    {
        this.jda = jda;
        this.serverMap = new HashMap<>();
        this.mediaCache = new MediaCache();
    
        this.jda.addEventListener(new ListenerAdapter()
        {
            @Override
            public void onMessageReceived(MessageReceivedEvent event)
            {
                if (event.getAuthor().isBot())
                {
                    return;
                }
                if (event.isPrivate())
                {
                    privateGuild.handleMessageEvent(event);
                    return;
                }
    
                if (!serverMap.containsKey(event.getGuild().getId())) addServer(event.getGuild());
                GnarManager server = serverMap.get(event.getGuild().getId());
                server.handleMessageEvent(event);
            }
    
            @Override
            public void onGuildMemberJoin(GuildMemberJoinEvent event)
            {
                if (!serverMap.containsKey(event.getGuild().getId())) addServer(event.getGuild());
                GnarManager server = serverMap.get(event.getGuild().getId());
                server.handleUserJoin(event);
            }
    
            @Override
            public void onGuildMemberLeave(GuildMemberLeaveEvent event)
            {
                if (!serverMap.containsKey(event.getGuild().getId())) addServer(event.getGuild());
                GnarManager server = serverMap.get(event.getGuild().getId());
                server.handleUserLeave(event);
            }
    
            @Override
            public void onShutdown(ShutdownEvent event)
            {
                getGnarManagers().forEach(GnarManager::saveFile);
                getMediaCache().store();
            }
        
            @Override
            public void onGuildJoin(GuildJoinEvent event)
            {
                DiscordBotsInfo.updateServerCount(jda);
            }
        
            @Override
            public void onGuildLeave(GuildLeaveEvent event)
            {
                DiscordBotsInfo.updateServerCount(jda);
            }
        });
    }
    
    private void addServer(Guild server)
    {
        serverMap.put(server.getId(), new GuildManager(server.getId(), this, server));
    }
    
    public GnarManager getGnarGuildByID(String accessID)
    {
        return serverMap.get(accessID);
    }
    
    public List<GnarManager> getGnarManagers()
    {
        return new ArrayList<>(serverMap.values());
    }
    
    public MediaCache getMediaCache()
    {
        return mediaCache;
    }
}
