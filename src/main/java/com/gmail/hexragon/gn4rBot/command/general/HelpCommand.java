package com.gmail.hexragon.gn4rBot.command.general;

import com.gmail.hexragon.gn4rBot.command.misc.GnarQuotes;
import com.gmail.hexragon.gn4rBot.managers.commands.Command;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.managers.users.PermissionLevel;
import net.dv8tion.jda.entities.Guild;
import net.dv8tion.jda.entities.Message;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Command(
		aliases = {"help", "guide"},
		usage = "[command]",
		description = "Display GN4R's list of commands."
)
public class HelpCommand extends CommandExecutor
{
	public void execute(Message message, String[] args)
	{
		if (args.length >= 1)
		{
			CommandExecutor cmd = getCommandManager().getCommand(args[0]);
			if (cmd == null)
			{
				message.getChannel().sendMessage(String.format("%s ➤ There is no command by that name. :cry:", message.getAuthor().getAsMention()));
				return;
			}
			
			List<String> aliases = getCommandManager().getCommandRegistry().entrySet().stream()
					.filter(entry -> entry.getValue() == cmd && !entry.getKey().equals(args[0]))
					.map(Map.Entry::getKey)
					.collect(Collectors.toList());
			
			StringJoiner joiner = new StringJoiner("\n");
			joiner.add(String.format("%s ➤ This is the information for the command `%s`:", message.getAuthor().getAsMention(), args[0]));
			joiner.add("```");
			joiner.add("Description:   " + cmd.getDescription());
			
			String usage = cmd.getUsage();
			
			joiner.add("Usage:         " + getCommandManager().getToken() + args[0].toLowerCase() + (usage == null ? "" : " " + usage));
			if (!aliases.isEmpty()) joiner.add("Aliases:       ["+StringUtils.join(aliases, ", ")+"]");
			joiner.add("```");
			
			message.getChannel().sendMessage(joiner.toString());
		}
		else
		{
			
			List<String> commands = new ArrayList<>(getCommandManager().getUniqueCommandRegistry().keySet());
			StringBuilder builder = new StringBuilder();
			
			Guild guild = message.getJDA().getGuildById(getGnarGuild().getAccessID());
			
			builder.append(" \n ```This is all of GN4R-Bot's currently registered commands as of ")
					.append(new SimpleDateFormat("MMMM F, yyyy hh:mm:ss a").format(new Date()))
					.append(" on the Discord server '")
					.append(guild != null ? guild.getName() : getGnarGuild().getAccessID())
					.append("'")
					.append(".```\n");
			
			builder.append("```\n[Bot Commander] commands requires you to have a role named exactly 'Bot Commander'.```\n");
			
			
			Arrays.stream(PermissionLevel.values()).forEach(permissionLevel ->
			{
				int cmdCount = 0;
				StringBuilder subBuilder = new StringBuilder();
				
				subBuilder.append("```xl\n");
				
				subBuilder.append('[').append(permissionLevel.toString().replaceAll("_", " ")).append(']');
				
				int _num = 30 - subBuilder.length();
				for (int i = 0; i < _num; i++) subBuilder.append("─");
				
				subBuilder.append("\n");
				
				for (String cmdString : commands)
				{
					CommandExecutor cmd = getCommandManager().getCommand(cmdString);
					
					if (cmd.permissionLevel() != permissionLevel || !cmd.isShownInHelp()) continue;
					else cmdCount++;
					
					String usage = cmd.getUsage();
					
					//cmd instanceof MusicCommandExecutor ? "♪ " : "  "
					//                -V-
					subBuilder.append("  ").append(getCommandManager().getToken());
					subBuilder.append(cmdString).append(usage == null ? "" : " " + usage).append("\n");
					
					//subBuilder.append("    ").append(cmd.getDescription()).append("\n");
				}
				
				subBuilder.append("```\n");
				
				if (cmdCount != 0) builder.append(subBuilder.toString());
			});
			
			builder.append("```\nNOTICE: Music capabilities will have to be disabled for now until we get a better server, sorry for the inconveniences!```\n");
			
			message.getChannel().sendMessage(String.format("%s ➤ **" + GnarQuotes.getRandomQuote() + "** My commands has been PM'ed to you.", message.getAuthor().getAsMention()));
			
			builder.append("```\nTo view a command's description, do '").append(getCommandManager().getToken()).append("help (command)'.```");
			message.getAuthor().getPrivateChannel().sendMessage(builder.toString());
		}
	}
}
