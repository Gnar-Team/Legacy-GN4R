package com.gmail.hexragon.gn4rBot.command.general;

import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandManager;
import net.dv8tion.jda.JDA;
import net.dv8tion.jda.entities.Guild;
import net.dv8tion.jda.entities.User;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

import java.util.StringJoiner;

public class BotInfoCommand extends CommandExecutor
{
	public BotInfoCommand(CommandManager manager)
	{
		super(manager);
		setDescription("Show information about Gn4r-Bot.");
	}

	public void execute(MessageReceivedEvent event, String[] args)
	{
		int textChannels = 0;
		int voiceChannels = 0;
		int servers = 0;
		int users = 0;
		int offline = 0;
		int online = 0;
		int inactive = 0;

		JDA jda = event.getJDA();

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
		joiner.add("   ├─[Creator]              Avalon");
		joiner.add("   ├─[Senpai]               Maeyrl");
		joiner.add("   ├─[# of Commands]        " + getCommandManager().getUniqueCommandRegistry().size());
		joiner.add("   ├─[Language]             Java 8");
		joiner.add("   ├─[Library]              Javacord");
		joiner.add("   ├─[Dev Server]           https://discord.me/avalonsdungeon");
		joiner.add("   └─[GitHub]               https://github.com/Hexragon/ReinBot");

		event.getChannel().sendMessage(String.format("%s ➤ Here is all of my information.", event.getAuthor().getAsMention()));

		event.getChannel().sendMessage("```xl\n" + joiner.toString() + "```\n");
}
}
