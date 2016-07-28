package com.gmail.hexragon.gn4rBot.command.general;

import com.gmail.hexragon.gn4rBot.command.misc.GnarQuotes;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandManager;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

public class InviteBotCommand extends CommandExecutor
{
	public InviteBotCommand()
	{
		
		setDescription("Get a link to invite GN4R to your server.");
	}
	
	@Override
	public void execute(MessageReceivedEvent event, String[] args)
	{
		event.getMessage().deleteMessage();
		event.getChannel().sendMessage("**" + GnarQuotes.getRandomQuote() + "** Want some GN4R?! https://discordapp.com/oauth2/authorize?client_id=201492375653056512&scope=bot&permissions=32014");
	}
}
