package com.gmail.hexragon.gn4rBot;

import com.gmail.hexragon.gn4rBot.managers.servers.ServerManagers;
import com.gmail.hexragon.gn4rBot.util.FileIOManager;
import com.gmail.hexragon.gn4rBot.util.PropertiesManager;
import net.dv8tion.jda.JDA;
import net.dv8tion.jda.JDABuilder;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import net.dv8tion.jda.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class GnarBot
{
	public static final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
	
	public static final List<String> ADMIN_IDS = new FileIOManager("_DATA/administrators").readList();
	public static final PropertiesManager TOKENS = new PropertiesManager().load(new File("_DATA/tokens.properties"));
	private static final long startTime = System.currentTimeMillis();
	
	public static void main(String[] args) throws Exception
	{
		File dataFolder = new File("_DATA");
		if (!dataFolder.exists())
		{
			System.out.println("[ERROR] - Folder '_DATA' not found.");
			return;
		}
		
		new GnarBot(TOKENS.get("main-bot"));
		
		//new SystemInfo().printUsage();
	}

	private GnarBot(String token)
	{
		ServerManagers serverManagers = new ServerManagers();
		
		try
		{
			final JDA jda = new JDABuilder().setBotToken(token).buildBlocking();

			jda.getAccountManager().setUsername("GN4R");
			jda.getAccountManager().setGame("_help | _invite");

			jda.setAutoReconnect(true);
			
			jda.addEventListener(new ListenerAdapter()
			{
				@Override
				public void onMessageReceived(MessageReceivedEvent event)
				{
					if (!event.getAuthor().isBot()) serverManagers.handleMessageEvent(event);
				}
			});

		}
		catch (LoginException | InterruptedException e)
		{
			e.printStackTrace();
		}

	}
	
	public static String getUptimeStamp()
	{
		long seconds = (new Date().getTime() - startTime) / 1000;
		long minutes = seconds / 60;
		long hours = minutes / 60;
		long days = hours / 24;
		return days + " days, " + hours % 24 + " hours, " + minutes % 60 + " minutes and " + seconds % 60 + " seconds";
	}
	
	public static String getShortUptimeStamp()
	{
		long seconds = (new Date().getTime() - startTime) / 1000;
		long minutes = seconds / 60;
		long hours = minutes / 60;
		long days = hours / 24;
		return days + "d " + hours % 24 + "h " + minutes % 60 + "m " + seconds % 60 + "s";
	}
}
