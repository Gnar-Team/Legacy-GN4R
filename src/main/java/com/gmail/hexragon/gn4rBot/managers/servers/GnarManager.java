package com.gmail.hexragon.gn4rBot.managers.servers;

import com.gmail.hexragon.gn4rBot.managers.commands.CommandManager;
import com.gmail.hexragon.gn4rBot.managers.users.UserManager;
import net.dv8tion.jda.entities.Guild;
import net.dv8tion.jda.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

import java.io.File;

public interface GnarManager
{
    CommandManager getCommandManager();
    
    UserManager getUserManager();
    
    String getAccessID();
    
    ServerManager getServerManager();
    
    void handleMessageEvent(MessageReceivedEvent event);
    
    void handleUserJoin(GuildMemberJoinEvent event);
    
    void handleUserLeave(GuildMemberLeaveEvent event);
    
    File getFile();
    
    void saveFile();
    
    Guild getGuild();
}
