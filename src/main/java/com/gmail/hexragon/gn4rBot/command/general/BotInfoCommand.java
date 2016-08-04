package com.gmail.hexragon.gn4rBot.command.general;

import com.gmail.hexragon.gn4rBot.GnarBot;
import com.gmail.hexragon.gn4rBot.command.misc.GnarQuotes;
import com.gmail.hexragon.gn4rBot.managers.commands.Command;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.util.GnarMessage;
import net.dv8tion.jda.JDA;
import net.dv8tion.jda.entities.Guild;
import net.dv8tion.jda.entities.User;

import java.util.StringJoiner;

@Command(
		aliases = {"info", "botinfo"},
		description = "Show information about GN4R-BOT."
)
public class BotInfoCommand extends CommandExecutor
{
	
	public void execute(GnarMessage message, String[] args)
	{
		int channels = 0;
		int textChannels = 0;
		int voiceChannels = 0;
		int servers = 0;
		int users = 0;
		int offline = 0;
		int online = 0;
		int inactive = 0;
		
		JDA jda = message.getJDA();
		
		for (Guild g : jda.getGuilds())
		{
			for (User u : g.getUsers())
			{
				switch (u.getOnlineStatus())
				{
					case ONLINE:
						online++;
						break;
					case OFFLINE:
						offline++;
						break;
					case AWAY:
						inactive++;
						break;
				}
			}
			
			servers++;
			users += g.getUsers().size();
			textChannels += g.getTextChannels().size();
			voiceChannels += g.getVoiceChannels().size();
			channels += g.getTextChannels().size() + g.getVoiceChannels().size();
		}
		
		int commandSize = getCommandManager().getUniqueCommandRegistry()//.values().stream()
				//.filter(CommandExecutor::isShownInHelp)
				//.collect(Collectors.toList())
				.size();
		
		int requests = getGnarGuild().getServerManager().getGnarGuilds().parallelStream()
				.mapToInt(guild -> guild.getCommandManager().getRequests())
				.sum();
		
		
		StringJoiner joiner = new StringJoiner("\n");
//		joiner.add("[Usage Statistics]");
//		joiner.add("   ├─[Requests]             " + requests);
//		joiner.add("   ├─[Servers]              " + servers);
//		joiner.add("   ├─[Text Channels]        " + textChannels);
//		joiner.add("   ├─[Voice Channels]       " + voiceChannels);
//		joiner.add("   └─[Users]                " + users);
//		joiner.add("        ├─[Online]          " + online);
//		joiner.add("        ├─[Offline]         " + offline);
//		joiner.add("        └─[Inactive]        " + inactive);
//		joiner.add("");
//		joiner.add("[Credits]");
//		joiner.add("   ├─[Creator]              Avalon & Maeyrl");
//		joiner.add("   ├─[# of Commands]        " + commandSize);
//		joiner.add("   ├─[Language]             Java 8");
//		joiner.add("   ├─[Library]              Javacord");
//		joiner.add("   └─[Uptime]               " + GnarBot.getUptimeStamp() + ".");
		
		joiner.add("\u258C Requests ____ " + requests);
		joiner.add("\u258C Servers _____ " + servers);
		joiner.add("\u258C");
		joiner.add("\u258C Channels ____ " + channels);
		joiner.add("\u258C  - Text _____ " + textChannels);
		joiner.add("\u258C  - Voice ____ " + voiceChannels);
		joiner.add("\u258C");
		joiner.add("\u258C Users _______ " + users);
		joiner.add("\u258C  - Online ___ " + online);
		joiner.add("\u258C  - Offline __ " + offline);
		joiner.add("\u258C  - Inactive _ " + inactive);
		joiner.add("\u258C");
		joiner.add("\u258C Creator _____ Avalon & Maeyrl");
		joiner.add("\u258C Commands ____ " + commandSize);
		joiner.add("\u258C Library _____ JDA");
		joiner.add("\u258C Uptime ______ " + GnarBot.getShortUptimeStamp() + ".");
		
		message.reply("**" + GnarQuotes.getRandomQuote() + "** Here is all of my information!");
		
		message.getChannel().sendMessage("```xl\n" + joiner.toString() + "```\n");
	}
}
