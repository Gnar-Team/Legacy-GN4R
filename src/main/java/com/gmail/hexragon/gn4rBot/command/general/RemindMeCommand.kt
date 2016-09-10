package com.gmail.hexragon.gn4rBot.command.general

import com.gmail.hexragon.gn4rBot.GnarBot
import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor
import com.gmail.hexragon.gn4rBot.managers.commands.annotations.Command
import com.gmail.hexragon.gn4rBot.managers.commands.annotations.ManagerDependent
import com.gmail.hexragon.gn4rBot.util.GnarMessage
import com.gmail.hexragon.gn4rBot.util.GnarQuotes
import java.util.concurrent.TimeUnit

@ManagerDependent
@Command(aliases = arrayOf("remindme", "remind"), usage = "(#) (unit) (msg)")
class RemindMeCommand : CommandExecutor()
{
    override fun execute(message : GnarMessage?, args : Array<out String>?)
    {
        if (args!!.size >= 3)
        {
            val string = args.copyOfRange(2, args.size).joinToString(" ")
            
            val time = try
            {
                args[0].toInt()
            }
            catch (e : NumberFormatException)
            {
                message?.reply("The time number was not an integer.")
                return
            }
            
            val timeUnit = try
            {
                TimeUnit.valueOf(args[1].toUpperCase())
            }
            catch (e : IllegalArgumentException)
            {
                message?.reply("The specified time unit was invalid.")
                return
            }
            
            if (time > 0)
            {
                message?.reply("**${GnarQuotes.getRandomQuote()}** I'll be reminding you in __$time ${timeUnit.toString().toLowerCase()}__.")
                
                GnarBot.scheduler.schedule({
                    message?.author?.privateChannel?.sendMessage("**REMINDER:** You requested to be reminded about this __$time ${timeUnit.toString().toLowerCase()}__ ago:\n```\n$string```")
                }, time.toLong(), timeUnit)
                
            }
            else
            {
                message?.reply("Number must be more than 0.")
            }
        }
        else
        {
            message?.reply("Insufficient amount of arguments.")
        }
    }
}