package com.gmail.hexragon.gn4rBot.managers.commands;

import com.gmail.hexragon.gn4rBot.managers.servers.GnarGuild;
import com.gmail.hexragon.gn4rBot.managers.users.PermissionLevel;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

public abstract class CommandExecutor
{
	private final CommandManager manager;
	private String description =  "No descriptions provided.";
	private String usage = null;
	private PermissionLevel permission = PermissionLevel.USER;
	private boolean showInHelp = true;

	protected CommandExecutor(CommandManager manager)
	{
		this.manager = manager;
	}

	public abstract void execute(MessageReceivedEvent event, String[] args);

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getDescription()
	{
		return description;
	}

	public void setPermission(PermissionLevel permission)
	{
		this.permission = permission;
	}

	public PermissionLevel permissionLevel()
	{
		return permission;
	}

	public CommandManager getCommandManager()
	{
		return manager;
	}

	public void setUsage(String usage)
	{
		this.usage = usage;
	}

	public String getUsage()
	{
		return usage;
	}

	public void showInHelp(boolean showInHelp)
	{
		this.showInHelp = showInHelp;
	}

	public boolean isShownInHelp()
	{
		return showInHelp;
	}

	public GnarGuild getGnarGuild()
	{
		return getCommandManager().getGnarGuild();
	}
}