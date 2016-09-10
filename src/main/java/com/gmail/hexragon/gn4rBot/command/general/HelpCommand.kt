package com.gmail.hexragon.gn4rBot.command.general

import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor
import com.gmail.hexragon.gn4rBot.managers.commands.annotations.Command
import com.gmail.hexragon.gn4rBot.managers.commands.annotations.ManagerDependent
import com.gmail.hexragon.gn4rBot.managers.users.PermissionLevel
import com.gmail.hexragon.gn4rBot.util.GnarMessage
import com.gmail.hexragon.gn4rBot.util.GnarQuotes
import java.util.StringJoiner

@ManagerDependent
@Command(aliases = arrayOf("help", "guide"), usage = "[command]", description = "Display GN4R's list of commands.")
class HelpCommand : CommandExecutor()
{
    override fun execute(message : GnarMessage?, args : Array<out String>?)
    {
        if (args!!.size >= 1)
        {
            val cmd : CommandExecutor? = commandManager.getCommand(args[0])
            
            if (cmd == null)
            {
                message?.replyRaw("There is no command by the name `${args[0]}` in this guild. :cry:")
                return
            }
            
            val aliases = commandManager.registry.entries
                    .filter { it.value == cmd }
                    .map { it.key }
            
            val string = listOf(
                    "```",
                    "\u258C Description __ ${cmd.description}",
                    "\u258C Usage ________ ${commandManager.token}${args[0].toLowerCase()} ${cmd.usage}",
                    "\u258C Aliases ______ [${aliases.joinToString(", ")}]",
                    "```"
            )
            
            message?.replyRaw(string.joinToString("\n"))
            
            return
        }
        
        val commandEntries = commandManager.uniqueRegistry
        
        val builder = StringBuilder()
        
        if (!guildManager.isPrivate)
            builder.append("\nThis is all of GN4R-Bot's currently registered commands on the __**${guild.name}**__ guild.\n\n")
        else
            builder.append("\nThis is all of GN4R-Bot's currently registered commands on the __**direct message**__ channel.\n\n")
        
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
        
        builder.append("To view a command's description, do `${commandManager.token}help [command]`.\n")
        builder.append("You can also chat and execute commands with Gnar privately, try it!\n\n")
        
        builder.append("**Bot Commander** commands requires you to have a role named exactly __Bot Commander__.\n")
        builder.append("**Server Owner** commands requires you to be the __Server Owner__ to execute.\n\n")
    
        builder.append("**Latest News:**\n")
        builder.append(" - `_ascii` is broken right now.\n")
        builder.append(" - Private messaging will be broken until JDA 3.x.\n\n")
        
        builder.append("**Website:** http://gnarbot.xyz\n")
        builder.append("**Discord Server:** http://discord.gg/NQRpmr2\n")
        
        message?.author?.privateChannel?.sendMessage(builder.toString())
        message?.reply("**${GnarQuotes.getRandomQuote()}** My commands has been PM'ed to you.")
    }
}