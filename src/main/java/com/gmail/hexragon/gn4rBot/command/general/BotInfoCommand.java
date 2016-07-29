package com.gmail.hexragon.gn4rBot.command.general;

import com.gmail.hexragon.gn4rBot.GnarBot;
import com.gmail.hexragon.gn4rBot.managers.commands.Command;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import net.dv8tion.jda.JDA;
import net.dv8tion.jda.entities.Guild;
import net.dv8tion.jda.entities.Message;
import net.dv8tion.jda.entities.User;

import java.util.StringJoiner;

@Command(
		aliases = {"info", "botinfo"},
		description = "Show information about GN4R-BOT."
)
public class BotInfoCommand extends CommandExecutor
{
	
	public void execute(Message message, String[] args)
	{
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
		}
		
		int commandSize = getCommandManager().getUniqueCommandRegistry()//.values().stream()
				//.filter(CommandExecutor::isShownInHelp)
				//.collect(Collectors.toList())
				.size();
		
		StringJoiner joiner = new StringJoiner("\n");
		joiner.add("[Usage Statistics]");
		joiner.add("   ├─[Servers]              " + servers);
		joiner.add("   ├─[Text Channels]        " + textChannels);
		joiner.add("   ├─[Voice Channels]       " + voiceChannels);
		joiner.add("   └─[Users]                " + users);
		joiner.add("        ├─[Online]          " + online);
		joiner.add("        ├─[Offline]         " + offline);
		joiner.add("        └─[Inactive]        " + inactive);
		joiner.add("");
		joiner.add("[Credits]");
		joiner.add("   ├─[Creator]              Avalon & Maeyrl");
		joiner.add("   ├─[# of Commands]        " + commandSize);
		joiner.add("   ├─[Language]             Java 8");
		joiner.add("   ├─[Library]              Javacord");
		joiner.add("   └─[Uptime]               " + GnarBot.getUptimeStamp() + ".");
		
		message.getChannel().sendMessage(String.format("%s ➤ Here is all of my information.", message.getAuthor().getAsMention()));
		
		message.getChannel().sendMessage("```xl\n" + joiner.toString() + "```\n");
	}
}
