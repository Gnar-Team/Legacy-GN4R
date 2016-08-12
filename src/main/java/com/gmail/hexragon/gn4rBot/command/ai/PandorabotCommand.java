package com.gmail.hexragon.gn4rBot.command.ai;

import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.managers.commands.annotations.Command;
import com.gmail.hexragon.gn4rBot.managers.commands.annotations.RequiresGuild;
import com.gmail.hexragon.gn4rBot.util.GnarMessage;
import com.google.code.chatterbotapi.ChatterBot;
import com.google.code.chatterbotapi.ChatterBotFactory;
import com.google.code.chatterbotapi.ChatterBotSession;
import com.google.code.chatterbotapi.ChatterBotType;
import org.apache.commons.lang3.StringUtils;

@RequiresGuild
@Command(
        aliases = {"pbot", "pandorabot"},
        usage = "(query)",
        description = "Talk to Pandora-Bot."
)
public class PandorabotCommand extends CommandExecutor
{
    private ChatterBotFactory factory = new ChatterBotFactory();
    private ChatterBot bot = null;
    private ChatterBotSession session = null;
    
    @Override
    public void execute(GnarMessage message, String[] args)
    {
        try
        {
            if (bot == null)
            {
                bot = factory.create(ChatterBotType.PANDORABOTS, "b0dafd24ee35a477");
                session = bot.createSession();
                message.reply("Pandora-Bot session created for the server.");
            }
            
            String input = StringUtils.join(args, " ");
            
            String output = session.think(input);
            message.replyRaw("**[PandoraBot]** ─ `" + output + "`");
        }
        catch (Exception e)
        {
            message.reply("PandoraBot has encountered an exception. Resetting PandoraBot.");
            bot = null;
        }
    }
}
