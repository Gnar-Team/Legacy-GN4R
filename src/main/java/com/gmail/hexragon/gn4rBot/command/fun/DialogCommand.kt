package com.gmail.hexragon.gn4rBot.command.`fun`

import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor
import com.gmail.hexragon.gn4rBot.managers.commands.annotations.Command
import com.gmail.hexragon.gn4rBot.util.GnarMessage
import org.apache.commons.lang3.StringUtils
import org.apache.commons.lang3.text.WordUtils
import java.util.StringJoiner

@Command(aliases = arrayOf("dialog"))
class DialogCommand : CommandExecutor()
{
    override fun execute(message : GnarMessage?, args : Array<out String>?)
    {
        val joiner = StringJoiner("\n", "```", "```")
        joiner.add("﻿ ___________________________ ")
        joiner.add("| Dialog          [_][☐][✖]|")
        joiner.add("|‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾|")
        
        val lines = WordUtils.wrap(StringUtils.join(args, ' '), 25).split("\n")
        lines.forEach {
            val builder = StringBuilder()
            repeat(25 - it.trim().length) { builder.append(' ') }
            joiner.add("| ${it.trim()}$builder |")
        }
        
        joiner.add("|   _________    ________   |")
        joiner.add("|  |   YES   |  |   NO   |  |")
        joiner.add("|   ‾‾‾‾‾‾‾‾‾    ‾‾‾‾‾‾‾‾   |")
        joiner.add(" ‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾ ")
        
        message?.replyRaw(joiner.toString())
    }
}