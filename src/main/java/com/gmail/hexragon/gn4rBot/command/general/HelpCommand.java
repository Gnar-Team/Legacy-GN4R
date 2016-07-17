package com.gmail.hexragon.gn4rBot.command.general;

import com.gmail.hexragon.gn4rBot.command.misc.GnarQuotes;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandManager;
import com.gmail.hexragon.gn4rBot.managers.users.PermissionLevel;
import net.dv8tion.jda.entities.Guild;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class HelpCommand extends CommandExecutor
{
	public HelpCommand(CommandManager manager)
	{
		super(manager);
		setDescription("Display this list of commands.");
	}

	public void execute(MessageReceivedEvent event, String[] args)
	{
		List<String> commands = new ArrayList<>(getCommandManager().getUniqueCommandRegistry().keySet());
		StringBuilder builder = new StringBuilder();

		Guild guild = event.getJDA().getGuildById(getGnarGuild().getAccessID());

		builder.append(" \n ```This is all of GN4R-Bot's currently registered commands as of ")
				.append(new SimpleDateFormat("MMMM F, yyyy hh:mm:ss a").format(new Date()))
				.append(" on the Discord server '")
				.append(guild != null ? guild.getName() : getGnarGuild().getAccessID())
				.append("'")
				.append(".```\n");


		Arrays.stream(PermissionLevel.values()).forEach(permissionLevel ->
		{
			int cmdCount = 0;
			StringBuilder subBuilder = new StringBuilder();

			subBuilder.append("```xl\n");

			subBuilder.append('[').append(permissionLevel.toString().replaceAll("_", " ")).append(']');

			int _num = 50 - subBuilder.length();
			for (int i = 0; i < _num; i++) subBuilder.append("─");

			subBuilder.append("\n");

			for (String cmdString : commands)
			{
				CommandExecutor cmd = getCommandManager().getCommand(cmdString);

				if (cmd.getPermission() != permissionLevel || !cmd.isShownInHelp()) continue;
				else cmdCount++;

				String usage = cmd.getUsage();

				subBuilder.append("  ").append(getCommandManager().getToken());
				subBuilder.append(usage != null ? usage : cmdString).append("\n");

				subBuilder.append("    ").append(cmd.getDescription()).append("\n");
			}

			subBuilder.append("```\n");

			if (cmdCount != 0) builder.append(subBuilder.toString());
		});

		event.getChannel().sendMessage(String.format("%s ➤ **" + GnarQuotes.getRandomQuote() + "** My commands has been PM'ed to you.", event.getAuthor().getAsMention()));

		event.getAuthor().getPrivateChannel().sendMessage(builder.toString());
//		event.getAuthor().getPrivateChannel().sendMessage(
//				String.format("```\nAre you an administrator of the server and want to obtain Gn4r's SERVER MASTER permission? Obtain the 'Manage Server' permission and type in chat '%sadminme'.```", getCommandManager().getToken()));
	}
}
