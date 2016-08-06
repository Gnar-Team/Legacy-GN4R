package com.gmail.hexragon.gn4rBot.command.general

import com.gmail.hexragon.gn4rBot.managers.commands.Command
import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor
import com.gmail.hexragon.gn4rBot.managers.users.PermissionLevel
import com.gmail.hexragon.gn4rBot.util.GnarMessage
import com.gmail.hexragon.gn4rBot.util.GnarQuotes
import java.text.SimpleDateFormat
import java.util.*

@Command(aliases = arrayOf("help", "guide"), usage = "[command]", description = "Display GN4R's list of commands.")
class HelpCommand : CommandExecutor()
{
	override fun execute(message : GnarMessage?, args : Array<out String>?)
	{
		if (args!!.size >= 1)
		{
			val cmd : CommandExecutor?  = commandManager.getCommand(args[0])
			
			if (cmd == null)
			{
				message?.replyRaw("There is no command by the name `${args[0]}`. :cry:")
				return
			}
			
			val aliases = commandManager.commandRegistry.entries
					.filter { it.value == cmd }
					.map { it.key }
			
			val joiner = StringJoiner("\n")
			joiner.add(message?.author?.asMention + " ➜ This is the information for the command `" + args[0] + "`:")
			joiner.add("```")
			joiner.add("\u258C Description __ ${cmd.description}")
			joiner.add("\u258C Usage ________ ${commandManager.token}${args[0].toLowerCase()} ${cmd.usage}")
			joiner.add("\u258C Aliases ______ ${aliases.joinToString(", ")}]")
			joiner.add("```")
			
			message?.replyRaw(joiner.toString())
		}
		
		val commandEntries = commandManager.uniqueCommandRegistry
		
		val builder = StringBuilder()
		
		builder.append("\nThis is all of GN4R-Bot's currently registered commands as of **${SimpleDateFormat("MMMM F, yyyy hh:mm:ss a").format(Date())}**.\n\n")
		
		PermissionLevel.values().forEach {
			val perm = it
			val count = commandEntries.values.filter { it.permissionLevel() == perm && it.isShownInHelp }.count()
			if (count < 1) return@forEach
			
			val joiner = StringJoiner("", "```xl\n", "```\n")
			
			val lineBuilder = StringBuilder()
			for (i in 0 .. 22 - perm.toString().length) lineBuilder.append('—')
			
			joiner.add("\u258c ${it.toString().replace("_", " ")} ${lineBuilder.toString()} $count\n")
			
			for ((label, cmd) in commandEntries)
			{
				if (cmd.permissionLevel() != perm || !cmd.isShownInHelp) continue
				
				joiner.add("\u258C  ${commandManager.token}$label ${cmd.usage}\n")
			}
			
			builder.append(joiner.toString())
		}
		
		builder.append("To view a command's description, do `${commandManager.token}help [command]`.\n\n")
		
		builder.append("**Bot Commander** commands requires you to have a role named exactly __Bot Commander__.\n")
		builder.append("**Server Owner** commands requires you to be the __Server Owner__ to execute.\n\n")
		
		message?.author?.privateChannel?.sendMessage(builder.toString())
		message?.channel?.sendMessage("${message.author?.asMention} ➜ **${GnarQuotes.getRandomQuote()}** My commands has been PM'ed to you.")
	}
}