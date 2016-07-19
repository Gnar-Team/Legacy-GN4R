package com.gmail.hexragon.gn4rBot.managers.commands;

import com.gmail.hexragon.gn4rBot.GnarBot;
import com.gmail.hexragon.gn4rBot.managers.servers.GnarGuild;
import com.gmail.hexragon.gn4rBot.managers.users.UserManager;
import com.gmail.hexragon.gn4rBot.util.Utils;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;

public class CommandManager
{
	private final GnarGuild server;

	private final Map<String, CommandExecutor> commandRegistry;
	
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
		Set<Class<? extends CommandExecutor>> blacklist = new HashSet<>();
		Map<String, CommandExecutor> uniqueMap = new LinkedHashMap<>();

		commandRegistry.entrySet().stream()
				.filter(entry -> !blacklist.contains(entry.getValue().getClass()))
				.forEach(entry ->
				{
					uniqueMap.put(entry.getKey(), entry.getValue());
					blacklist.add(entry.getValue().getClass());
				});

		return uniqueMap;
	}

	public void callCommand(MessageReceivedEvent event)
	{
		if (event.getAuthor().isBot()) return;
		boolean directMention = false;

		String messageContent = event.getMessage().getContent();

		if (messageContent.startsWith(token))
		{
			if (messageContent.startsWith(token+"gnar:"))
			{
				messageContent = messageContent.replaceFirst(token+"gnar:", token);
				directMention = true;
			}

			// Splitting sections
			String[] sections = messageContent.split(" ");
			String label = sections[0];

			// Parsing arguments
			String[] args = new String[sections.length - 1];
			int i = 0;
			for (String arg : sections)
			{
				if (!arg.equalsIgnoreCase(label))
				{
					args[i] = sections[i + 1];
					i++;
				}
			}

			for (String regCommand : commandRegistry.keySet())
			{
				if (label.equalsIgnoreCase(token + regCommand))
				{
					// Calling the command class.
					CommandExecutor cmd = commandRegistry.get(regCommand);

					if (cmd.getPermission().value > server.getUserManager().getGnarUser(event.getAuthor()).getPermission().value)
					{
						event.getChannel().sendMessage(String.format("%s ➤ You need to be %s or higher to use this command.", event.getAuthor().getAsMention(), cmd.getPermission().toString()));
						return;
					}

					// execute command
					try
					{
						cmd.execute(event, args);
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
						event.getChannel().sendMessage(String.format("%s ➤ A fatal error occured while executing this command.", event.getAuthor().getAsMention()));
					}
					
					return;
				}
			}
			if (directMention)
			{
				Map<String, Integer> levenshteinMap = new LinkedHashMap<>();

				getCommandRegistry().keySet()
						.forEach(cmd0 -> levenshteinMap.put(cmd0,StringUtils.getLevenshteinDistance(label, cmd0)));

				List<String> nearest = new ArrayList<>();
				int lowestLev = 5;

				for (Map.Entry<String, Integer> entry : levenshteinMap.entrySet())
				{
					if (entry.getValue() < lowestLev)
					{
						lowestLev = entry.getValue();
						nearest.clear();
						nearest.add(token+entry.getKey());
					}
					else if (entry.getValue() == lowestLev)
					{
						nearest.add(token+entry.getKey());
					}
				}
				event.getChannel().sendMessage(String.format("%s ➤ Invalid command.%s", event.getAuthor().getAsMention(), nearest.size() > 0 ? " Nearest commands: `"+Arrays.toString(nearest.toArray())+"`":""));

			}
		}
	}

	private void registerCommand(String label, Class<? extends CommandExecutor> cls)
	{
		try
		{
			if (!commandRegistry.isEmpty())
			{
				for (String command : commandRegistry.keySet())
				{
					if (label.equals(command))
					{
						System.out.println("Command is already registered.");
						return;
					}
				}
			}
			CommandExecutor cmd = cls.getDeclaredConstructor(this.getClass()).newInstance(this);

			commandRegistry.put(label, cmd);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void unregisterCommand(String s)
	{
		if (!commandRegistry.isEmpty())
		{
			commandRegistry.keySet().stream().filter(s::equals).forEach(command -> commandRegistry.remove(s));
			return;
		}
		System.out.println("Command isn't registered.");
	}


	public CommandBuilder builder(String... aliases)
	{
		return new CommandBuilder(aliases);
	}

	public class CommandBuilder
	{
		final String[] aliases;
		private Class<? extends CommandExecutor> executor;

		protected CommandBuilder(String... aliases)
		{
			this.aliases = aliases;
		}

		public void executor(Class<? extends CommandExecutor> executor)
		{
			this.executor = executor;
			for (String alias : aliases) registerCommand(alias, executor);
		}
	}
}
