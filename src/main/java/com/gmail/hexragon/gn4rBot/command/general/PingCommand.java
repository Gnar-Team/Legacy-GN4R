package com.gmail.hexragon.gn4rBot.command.general;

import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.managers.commands.annotations.Command;
import com.gmail.hexragon.gn4rBot.util.GnarMessage;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;

@Command(
        aliases = "ping",
        description = "Show the bot's current response time.."
)
public class PingCommand extends CommandExecutor
{
    @Override
    public void execute(GnarMessage message, String[] args)
    {
        OffsetDateTime sentTime = message.getTime();
        OffsetDateTime responseTime = OffsetDateTime.now();
        
        message.reply("Response time: `" + Math.abs(sentTime.until(responseTime, ChronoUnit.MILLIS)) + "ms`.");
    }
}
