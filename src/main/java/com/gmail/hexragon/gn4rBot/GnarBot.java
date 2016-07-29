package com.gmail.hexragon.gn4rBot;

import com.gmail.hexragon.gn4rBot.managers.servers.ServerManagers;
import com.gmail.hexragon.gn4rBot.util.FileIOManager;
import com.gmail.hexragon.gn4rBot.util.PropertiesManager;
import com.gmail.hexragon.gn4rBot.util.Utils;
import net.dv8tion.jda.JDA;
import net.dv8tion.jda.JDABuilder;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import net.dv8tion.jda.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class GnarBot
{
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
		
		new GnarBot(TOKENS.get("beta-bot"));
	}

	private GnarBot(String token)
	{
		ServerManagers serverManagers = new ServerManagers();
		
		try
		{
			final JDA jda = new JDABuilder().setBotToken(token).buildBlocking();

			jda.getAccountManager().setUsername("GN4R");
			jda.getAccountManager().setGame("Fluff Ball");

			jda.setAutoReconnect(true);
			
			jda.addEventListener(new ListenerAdapter()
			{
				@Override
				public void onMessageReceived(MessageReceivedEvent event)
				{
					try
					{
						if (!event.getAuthor().isBot()) serverManagers.handleMessageEvent(event);
					}
					catch (Exception e)
					{
						if (GnarBot.ADMIN_IDS.contains(event.getAuthor().getId()))
						{
							event.getAuthor().getPrivateChannel().sendMessage(
									String.format("Error occured on server '%s' at %s while executing statement '%s'",
											event.getGuild().getName(),
											new SimpleDateFormat("MMMM F, yyyy hh:mm:ss a").format(new Date()),
											event.getMessage().getContent()));
							event.getAuthor().getPrivateChannel().sendMessage(Utils.exceptionToString(e));
						}
						event.getChannel().sendMessage(String.format("%s ➤ A exception occured while executing this command.", event.getAuthor().getAsMention()));

						e.printStackTrace();
					}
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
}
