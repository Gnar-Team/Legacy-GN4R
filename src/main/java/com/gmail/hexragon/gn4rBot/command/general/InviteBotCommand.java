package com.gmail.hexragon.gn4rBot.command.general;

import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.managers.commands.annotations.Command;
import com.gmail.hexragon.gn4rBot.util.GnarMessage;
import com.gmail.hexragon.gn4rBot.util.GnarQuotes;
import net.dv8tion.jda.Permission;

@Command(
        aliases = {"invite", "invitebot"},
        description = "Get a link to invite GN4R to your server."
)
public class InviteBotCommand extends CommandExecutor
{
    @Override
    public void execute(GnarMessage message, String[] args)
    {
        message.replyRaw("**" + GnarQuotes.getRandomQuote() + "** Want some GN4R on your server?!\n**0Auth Link:** " + message.getJDA().getSelfInfo().getAuthUrl(Permission.ADMINISTRATOR));
    }
}
