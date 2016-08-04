package com.gmail.hexragon.gn4rBot.managers.commands;

import com.gmail.hexragon.gn4rBot.GnarBot;
import com.gmail.hexragon.gn4rBot.managers.servers.GnarGuild;
import com.gmail.hexragon.gn4rBot.managers.users.UserManager;
import com.gmail.hexragon.gn4rBot.util.GnarMessage;
import com.gmail.hexragon.gn4rBot.util.Utils;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;

public class CommandManager
{
	private final GnarGuild server;

	private final Map<String, CommandExecutor> commandRegistry;
	
	private int requests = 0;
	
	private String token = "_"; //default token

	public CommandManager(GnarGuild server)
	{
		this.server = server;
		commandRegistry = new LinkedHashMap<>();
	}

	public String getToken()
	{
		return token;
	}

	public void setToken(String token)
	{
		this.token = token;
	}

	public CommandExecutor getCommand(String key)
	{
		return getCommandRegistry().get(key);
	}

	public UserManager getUserManager()
	{
		return server.getUserManager();
	}

	public GnarGuild getGnarGuild()
	{
		return server;
	}

	public Map<String, CommandExecutor> getCommandRegistry()
	{
		return commandRegistry;
	}

	public Map<String, CommandExecutor> getUniqueCommandRegistry()
	{
		Set<CommandExecutor> blacklist = new HashSet<>();
		Map<String, CommandExecutor> uniqueMap = new LinkedHashMap<>();

		commandRegistry.entrySet().stream()
				.filter(entry -> !blacklist.contains(entry.getValue()))
				.forEach(entry ->
				{
					uniqueMap.put(entry.getKey(), entry.getValue());
					blacklist.add(entry.getValue());
				});

		return uniqueMap;
	}

	public void callCommand(MessageReceivedEvent event)
	{
		//boolean directMention = false;

		String messageContent = event.getMessage().getContent();

		if (messageContent.startsWith(token))
		{
			if (messageContent.startsWith(token+"gnar:"))
			{
				messageContent = messageContent.replaceFirst(token+"gnar:", token);
				//directMention = true;
			}

			// Splitting sections
			String[] sections = messageContent.split(" ");
			
			String label = sections[0];
			String[] args = Arrays.copyOfRange(sections, 1, sections.length);

			for (String regCommand : commandRegistry.keySet())
			{
				if (label.equalsIgnoreCase(token + regCommand))
				{
					// Calling the command class.
					CommandExecutor cmd = commandRegistry.get(regCommand);

					if (cmd.permissionLevel().value > server.getUserManager().getGnarUser(event.getAuthor()).getPermission().value)
					{
						event.getChannel().sendMessage(String.format("%s ➜ You need to be %s or higher to use this command.", event.getAuthor().getAsMention(), cmd.permissionLevel().toString()));
						return;
					}

					// execute command
					try
					{
						cmd.execute(new GnarMessage(event.getMessage()), args);
						requests++;
					}
					catch (Exception e)
					{
						if (GnarBot.ADMIN_IDS.contains(event.getAuthor().getId()))
						{
							event.getAuthor().getPrivateChannel().sendMessage(
									String.format("Error ➜ Server: `%s` | Time: `%s` | Statement: `%s`",
											event.getGuild().getName(),
											new SimpleDateFormat("MMMM F, yyyy hh:mm:ss a").format(new Date()),
											event.getMessage().getContent()));
							event.getAuthor().getPrivateChannel().sendMessage(Utils.exceptionToString(e));
						}
						event.getChannel().sendMessage(String.format("%s ➜ An exception occurred while executing this command.", event.getAuthor().getAsMention()));
						e.printStackTrace();
					}
					
					return;
				}
			}
//			if (directMention)
//			{
//				Map<String, Integer> levenshteinMap = new LinkedHashMap<>();
//
//				getCommandRegistry().keySet()
//						.forEach(cmd0 -> levenshteinMap.put(cmd0,StringUtils.getLevenshteinDistance(label, cmd0)));
//
//				List<String> nearest = new ArrayList<>();
//				int lowestLev = 5;
//
//				for (Map.Entry<String, Integer> entry : levenshteinMap.entrySet())
//				{
//					if (entry.getValue() < lowestLev)
//					{
//						lowestLev = entry.getValue();
//						nearest.clear();
//						nearest.add(token+entry.getKey());
//					}
//					else if (entry.getValue() == lowestLev)
//					{
//						nearest.add(token+entry.getKey());
//					}
//				}
//				event.getChannel().sendMessage(String.format("%s ➤ Invalid command.%s", event.getAuthor().getAsMention(), nearest.size() > 0 ? " Nearest commands: `"+Arrays.toString(nearest.toArray())+"`":""));
//
//			}
		}
	}
	
	public void registerCommand(Class<? extends CommandExecutor> cls) throws IllegalStateException
	{
		try
		{
			CommandExecutor cmd = cls.newInstance();
			
			Command meta = cls.getAnnotation(Command.class);
			
			if (meta == null)
			{
				throw new IllegalStateException(cls + ": @Command annotation not found.");
			}
			
			cmd.setDescription(meta.description());
			cmd.setPermission(meta.permissionRequired());
			cmd.showInHelp(meta.showInHelp());
			cmd.setUsage(meta.usage());
			
			for (Field field : cmd.getClass().getSuperclass().getDeclaredFields())
			{
				if (field.getType() == this.getClass())
				{
					field.setAccessible(true);
					field.set(cmd, this);
				}
			}
			
			Arrays.stream(meta.aliases()).forEach(s -> registerCommand(s, cmd));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void registerCommand(String label, CommandExecutor cmd)
	{
		if (!commandRegistry.isEmpty())
		{
			for (String command : commandRegistry.keySet())
			{
				if (label.equals(command))
				{
					System.out.println(label);
					System.out.println("Command is already registered.");
					return;
				}
			}
		}
		commandRegistry.put(label, cmd);
	}
	
	public void unregisterCommand(String label)
	{
		if (!commandRegistry.isEmpty())
		{
			commandRegistry.keySet().stream().filter(label::equals).forEach(command -> commandRegistry.remove(label));
			return;
		}
		System.out.println("Command isn't registered.");
	}
	
	public int getRequests()
	{
		return requests;
	}
}
