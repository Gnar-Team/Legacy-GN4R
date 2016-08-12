package com.gmail.hexragon.gn4rBot.command.admin

import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor
import com.gmail.hexragon.gn4rBot.managers.commands.annotations.Command
import com.gmail.hexragon.gn4rBot.managers.users.PermissionLevel
import com.gmail.hexragon.gn4rBot.util.GnarMessage

@Command(aliases = arrayOf("throwError"), permissionRequired = PermissionLevel.BOT_MASTER, showInHelp = false)
class ThrowError : CommandExecutor()
{
    override fun execute(message : GnarMessage?, args : Array<out String>?)
    {
        throw RuntimeException("Requested to throw an error, so here you go.")
    }
}