package com.gmail.hexragon.gn4rBot.testing

import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor
import com.gmail.hexragon.gn4rBot.managers.commands.CommandManager
import net.dv8tion.jda.events.message.MessageReceivedEvent

class KOTLIN_KotlinBase(cm : CommandManager) : CommandExecutor(cm)
{
	init
	{
		description = "Kotlin test command."
		showInHelp(false)
	}

//	constructor(cm : CommandManager, usage : String = "wow") : this(cm)
//	{
//		setUsage("kotlintest")
//	}

	override fun execute(event : MessageReceivedEvent?, args : Array<String>?)
	{
		event?.channel?.sendMessage("This command is made using Kotlin!")

	}
}