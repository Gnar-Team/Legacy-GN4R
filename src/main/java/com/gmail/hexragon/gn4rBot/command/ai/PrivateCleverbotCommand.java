package com.gmail.hexragon.gn4rBot.command.ai;

import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.managers.commands.annotations.Command;
import com.gmail.hexragon.gn4rBot.util.GnarMessage;
import com.google.code.chatterbotapi.ChatterBot;
import com.google.code.chatterbotapi.ChatterBotFactory;
import com.google.code.chatterbotapi.ChatterBotSession;
import com.google.code.chatterbotapi.ChatterBotType;
import net.dv8tion.jda.entities.User;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.WeakHashMap;

@Command(
        aliases = {"cbot", "cleverbot"},
        usage = "(query)",
        description = "Talk to Clever-Bot."
)
public class PrivateCleverbotCommand extends CommandExecutor
{
    private ChatterBotFactory factory = new ChatterBotFactory();
    private ChatterBotSession session = null;
    
    private Map<User, ChatterBotSession> sessionMap = new WeakHashMap<>();
    
    @Override
    public void execute(GnarMessage message, String[] args)
    {
        try
        {
            if (!sessionMap.containsKey(message.getAuthor()))
            {
                ChatterBot bot = factory.create(ChatterBotType.CLEVERBOT);
                sessionMap.put(message.getAuthor(), bot.createSession());
            }
            message.replyRaw(sessionMap.get(message.getAuthor()).think(StringUtils.join(args, " ")));
        }
        catch (Exception e)
        {
            message.reply("Chat Bot encountered an exception. Restarting. `:[`");
            sessionMap.remove(message.getAuthor());
        }
    }
}
