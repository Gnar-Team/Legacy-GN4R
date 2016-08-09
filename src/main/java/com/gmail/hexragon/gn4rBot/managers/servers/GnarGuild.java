package com.gmail.hexragon.gn4rBot.managers.servers;

import com.gmail.hexragon.gn4rBot.managers.commands.CommandManager;
import com.gmail.hexragon.gn4rBot.managers.users.UserManager;
import net.dv8tion.jda.entities.Guild;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

public interface GnarGuild
{
	CommandManager getCommandManager();
	
	UserManager getUserManager();
	
	String getAccessID();
	
	ServerManager getServerManager();
	
	void handleMessageEvent(MessageReceivedEvent event);
	
	String getBasePath();
	
	Guild getGuild();
}
