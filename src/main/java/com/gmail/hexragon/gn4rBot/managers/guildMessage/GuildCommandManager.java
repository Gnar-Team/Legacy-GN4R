package com.gmail.hexragon.gn4rBot.managers.guildMessage;

import com.gmail.hexragon.gn4rBot.GnarBot;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandManager;
import com.gmail.hexragon.gn4rBot.managers.commands.annotations.Command;
import com.gmail.hexragon.gn4rBot.managers.servers.GnarGuild;
import com.gmail.hexragon.gn4rBot.util.GnarMessage;
import com.gmail.hexragon.gn4rBot.util.Utils;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

import java.lang.reflect.Field;
import java.util.*;

public class GuildCommandManager implements CommandManager
{
	protected final GnarGuild server;

	protected final Map<String, CommandExecutor> commandRegistry;
	
	protected int requests = 0;
	
	protected String token = "_"; //default token
	
	public GuildCommandManager(GnarGuild server)
	{
		this.server = server;
		commandRegistry = new LinkedHashMap<>();
	}

	@Override
	public String getToken()
	{
		return token;
	}

	@Override
	public void setToken(String token)
	{
		this.token = token;
	}

	@Override
	public CommandExecutor getCommand(String key)
	{
		return getCommandRegistry().get(key);
	}

	@Override
	public GnarGuild getGnarGuild()
	{
		return server;
	}

	@Override
	public Map<String, CommandExecutor> getCommandRegistry()
	{
		return commandRegistry;
	}

	@Override
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

	@Override
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
			
			GnarMessage gMessage = new GnarMessage(event.getMessage());

			for (String regCommand : commandRegistry.keySet())
			{
				if (label.equalsIgnoreCase(token + regCommand))
				{
					// Calling the command class.
					CommandExecutor cmd = commandRegistry.get(regCommand);
					
					if (getGnarGuild().getUserManager() != null && cmd.permissionLevel().value > server.getUserManager().getGnarUser(event.getAuthor()).getPermission().value)
					{
						gMessage.reply("You do not have sufficient permission to use this command.");
						return;
					}

					// execute command
					try
					{
						cmd.execute(gMessage, args);
						requests++;
					}
					catch (Exception e)
					{
						if (GnarBot.ADMIN_IDS.contains(event.getAuthor().getId()))
						{
							event.getAuthor().getPrivateChannel().sendMessage(Utils.exceptionToString(e));
						}
						gMessage.reply("An exception occurred while executing this command.");
						e.printStackTrace();
					}
					
					return;
				}
			}
		}
	}
	
	@Override
	public void registerCommand(Class<? extends CommandExecutor> cls) throws IllegalStateException
	{
		if (!cls.isAnnotationPresent(Command.class))
		{
			throw new IllegalStateException(cls + ": @Command annotation not found.");
		}
		
		try
		{
			CommandExecutor cmd = cls.newInstance();
			
			Command meta = cls.getAnnotation(Command.class);
			
			cmd.setDescription(meta.description());
			cmd.setPermission(meta.permissionRequired());
			cmd.showInHelp(meta.showInHelp());
			cmd.setUsage(meta.usage());
			
			for (Field field : cmd.getClass().getSuperclass().getDeclaredFields())
			{
				if (field.getType().isAssignableFrom(this.getClass()))
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
	
	@Override
	public void unregisterCommand(String label)
	{
		if (!commandRegistry.isEmpty())
		{
			commandRegistry.keySet().stream().filter(label::equals).forEach(command -> commandRegistry.remove(label));
			return;
		}
		System.out.println("Command isn't registered.");
	}
	
	@Override
	public int getRequests()
	{
		return requests;
	}
}
