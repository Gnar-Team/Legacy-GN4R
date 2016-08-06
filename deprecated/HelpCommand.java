package com.gmail.hexragon.gn4rBot.command.general;

import com.gmail.hexragon.gn4rBot.managers.commands.Command;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.managers.users.PermissionLevel;
import com.gmail.hexragon.gn4rBot.util.GnarMessage;
import com.gmail.hexragon.gn4rBot.util.GnarQuotes;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.stream.Collectors;

@Command(
		aliases = {"help", "guide"},
		usage = "[command]",
		description = "Display GN4R's list of commands."
)
public class HelpCommand extends CommandExecutor
{
	public void execute(GnarMessage message, String[] args)
	{
		if (args.length >= 1)
		{
			CommandExecutor cmd = getCommandManager().getCommand(args[0]);
			if (cmd == null)
			{
				message.reply("There is no command by that name. :cry:");
				return;
			}
			
			List<String> aliases = getCommandManager().getCommandRegistry().entrySet().stream()
					.filter(entry -> entry.getValue() == cmd && !entry.getKey().equals(args[0]))
					.map(Map.Entry::getKey)
					.collect(Collectors.toList());
			
					
			StringJoiner joiner = new StringJoiner("\n");
			joiner.add(message.getAuthor().getAsMention() + " ➜ This is the information for the command `" + args[0] + "`:");
			joiner.add("```");
			joiner.add("\u258C Description __ " + cmd.getDescription());
			
			String usage = cmd.getUsage();
			
			joiner.add("\u258C Usage ________ " + getCommandManager().getToken() + args[0].toLowerCase() + (usage == null ? "" : " " + usage));
			if (!aliases.isEmpty()) joiner.add("\u258C Aliases ______ "+StringUtils.join(aliases, ", ")+"]");
			joiner.add("```");
			
			message.replyRaw(joiner.toString());
		}
		else
		{
			
			List<String> commands = new ArrayList<>(getCommandManager().getUniqueCommandRegistry().keySet());
			StringBuilder builder = new StringBuilder();
			
			builder.append("\nThis is all of GN4R-Bot's currently registered commands as of **")
					.append(new SimpleDateFormat("MMMM F, yyyy hh:mm:ss a").format(new Date()))
					.append("**.\n\n");
			
			Arrays.stream(PermissionLevel.values()).forEach(permissionLevel ->
			{
				int cmdCount = 0;
				StringBuilder subBuilder = new StringBuilder();
				
				subBuilder.append("```xl\n");
				
				subBuilder.append("\u258C ").append(permissionLevel.toString().replaceAll("_", " ")).append(" ");
				
				int _num = 35 - subBuilder.length();
				for (int i = 0; i < _num; i++) subBuilder.append("_");
				
				subBuilder.append("\n");
				
				for (String cmdString : commands)
				{
					CommandExecutor cmd = getCommandManager().getCommand(cmdString);
					
					if (cmd.permissionLevel() != permissionLevel || !cmd.isShownInHelp()) continue;
					else cmdCount++;
					
					String usage = cmd.getUsage();
					
					//cmd instanceof MusicCommandExecutor ? "♪ " : "  "
					//                -V-
					subBuilder.append("\u258C  ").append(getCommandManager().getToken());
					subBuilder.append(cmdString).append(usage == null ? "" : " " + usage).append("\n");
					
					//subBuilder.append("    ").append(cmd.getDescription()).append("\n");
				}
				
				subBuilder.append("```\n");
				
				if (cmdCount != 0) builder.append(subBuilder.toString());
			});
			
			builder.append("To view a command's description, do `").append(getCommandManager().getToken()).append("help [command]`.\n\n");
			
			builder.append("**Bot Commander** commands requires you to have a role named exactly __Bot Commander__.\n");
			builder.append("**Server Owner** commands requires you to be the __Server Owner__ to execute.\n\n");
			
			message.getAuthor().getPrivateChannel().sendMessage(builder.toString());
			message.getChannel().sendMessage(String.format("%s ➜ **" + GnarQuotes.getRandomQuote() + "** My commands has been PM'ed to you.", message.getAuthor().getAsMention()));
		}
	}
}
