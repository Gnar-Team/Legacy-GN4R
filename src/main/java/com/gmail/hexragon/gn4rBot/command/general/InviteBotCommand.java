package com.gmail.hexragon.gn4rBot.command.general;

import com.gmail.hexragon.gn4rBot.command.misc.GnarQuotes;
import com.gmail.hexragon.gn4rBot.managers.commands.Command;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import net.dv8tion.jda.Permission;
import net.dv8tion.jda.entities.Message;

@Command(
		aliases = {"invite", "invitebot"},
		description = "Get a link to invite GN4R to your server."
)
public class InviteBotCommand extends CommandExecutor
{
	@Override
	public void execute(Message message, String[] args)
	{
		message.deleteMessage();
		message.getChannel().sendMessage("**" + GnarQuotes.getRandomQuote() + "** Want some GN4R on your server?!\n**0Auth Link:** " + message.getJDA().getSelfInfo().getAuthUrl(Permission.ADMINISTRATOR));
	}
}
