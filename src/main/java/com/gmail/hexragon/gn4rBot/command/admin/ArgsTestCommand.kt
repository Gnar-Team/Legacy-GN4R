package com.gmail.hexragon.gn4rBot.command.admin

import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor
import com.gmail.hexragon.gn4rBot.managers.commands.annotations.Command
import com.gmail.hexragon.gn4rBot.managers.users.PermissionLevel
import com.gmail.hexragon.gn4rBot.util.GnarMessage

@Command(aliases = arrayOf("args"), permissionRequired = PermissionLevel.BOT_MASTER, showInHelp = false)
class ArgsTestCommand : CommandExecutor()
{
	override fun execute(message : GnarMessage?, args : Array<out String>?)
	{
		message?.reply("Args: `${args?.joinToString(", ")}`.")
	}
}