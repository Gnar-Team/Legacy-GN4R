package com.gmail.hexragon.gn4rBot.managers.commands;

import com.gmail.hexragon.gn4rBot.managers.servers.GnarGuild;
import com.gmail.hexragon.gn4rBot.managers.users.PermissionLevel;
import com.gmail.hexragon.gn4rBot.managers.users.UserManager;
import com.gmail.hexragon.gn4rBot.util.GnarMessage;

public abstract class CommandExecutor
{
	//injected by CommandManager
	private CommandManager manager;
	
	private String description = "No descriptions provided.";
	private String usage = null;
	private PermissionLevel permission = PermissionLevel.USER;
	private boolean showInHelp = true;

	protected CommandExecutor() {}

	public abstract void execute(GnarMessage message, String[] args);

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
	
	public UserManager getUserManager()
	{
		return getGnarGuild().getUserManager();
	}
	
	public CommandManager getCommandManager()
	{
		return manager;
	}
}