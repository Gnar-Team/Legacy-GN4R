package com.gmail.hexragon.gn4rBot.managers.users;

public enum PermissionLevel
{
	BOT_MASTER(3),
	SERVER_OWNER(2),
	BOT_COMMANDER(1),
	SERVER_USER(0),
	BOT(-1);

	public final int value;

	PermissionLevel(int level)
	{
		this.value = level;
	}

	public static PermissionLevel[] serverValues()
	{
		return new PermissionLevel[]{SERVER_OWNER, BOT_COMMANDER, SERVER_USER};
	}
}
