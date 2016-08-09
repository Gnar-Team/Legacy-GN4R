package com.gmail.hexragon.gn4rBot.managers.commands;

import com.gmail.hexragon.gn4rBot.managers.servers.GnarManager;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

import java.util.Map;

public interface CommandManager
{
	String getToken();
	
	void setToken(String token);
	
	CommandExecutor getCommand(String key);
	
	GnarManager getGnarManager();
	
	Map<String, CommandExecutor> getCommandRegistry();
	
	Map<String, CommandExecutor> getUniqueCommandRegistry();
	
	void callCommand(MessageReceivedEvent event);
	
	void registerCommand(Class<? extends CommandExecutor> cls) throws IllegalStateException;
	
	void unregisterCommand(String label);
	
	int getRequests();
}
