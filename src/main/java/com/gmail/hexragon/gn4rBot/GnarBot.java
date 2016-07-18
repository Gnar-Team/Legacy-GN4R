package com.gmail.hexragon.gn4rBot;

import com.gmail.hexragon.gn4rBot.managers.servers.GuildManager;
import com.gmail.hexragon.gn4rBot.util.FileIOManager;
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
	@SuppressWarnings("FieldCanBeLocal")
	private static String TOKEN = new FileIOManager("_DATA/token").readString();
	public static List<String> ADMIN_IDS = new FileIOManager("_DATA/administrators").readList();
	
	private static GnarBot instance;

	public static void main(String[] args) throws Exception
	{
		File dataFolder = new File("_DATA");
		if (!dataFolder.exists())
		{
			System.out.println("[ERROR] - Folder '_DATA' not found.");
			return;
		}

		instance = new GnarBot(TOKEN);
	}

	private GnarBot(String token)
	{
		GuildManager guildManager = new GuildManager();

		File f = new File("_DATA/images/pics/");
		f.mkdirs();
		f.deleteOnExit();

		try
		{
			final JDA jda = new JDABuilder().setBotToken(token).buildBlocking();

			jda.getAccountManager().setUsername("GN4R");
			jda.getAccountManager().setGame("League of Cuddles");

			jda.setAutoReconnect(true);

			jda.addEventListener(new ListenerAdapter()
			{
				@Override
				public void onMessageReceived(MessageReceivedEvent event)
				{
					try
					{
						if (!event.getAuthor().isBot()) guildManager.messageEvent(event);
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

	public static GnarBot getInstance()
	{
		return instance;
	}
}
