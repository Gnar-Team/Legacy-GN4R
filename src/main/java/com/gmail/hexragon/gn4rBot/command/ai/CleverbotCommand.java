package com.gmail.hexragon.gn4rBot.command.ai;

import com.gmail.hexragon.gn4rBot.managers.commands.Command;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.google.code.chatterbotapi.ChatterBot;
import com.google.code.chatterbotapi.ChatterBotFactory;
import com.google.code.chatterbotapi.ChatterBotSession;
import com.google.code.chatterbotapi.ChatterBotType;
import net.dv8tion.jda.entities.Message;
import org.apache.commons.lang3.StringUtils;

@Command(
		aliases = {"cbot", "cleverbot"},
		usage = "(query)",
		description = "Talk to Clever-Bot."
)
public class CleverbotCommand extends CommandExecutor
{
	private ChatterBotFactory factory = null;
	private ChatterBot bot = null;
	private ChatterBotSession session = null;
	
	@Override
	public void execute(Message message, String[] args)
	{
		
		try
		{
			if (factory == null)
			{
				factory = new ChatterBotFactory();
				bot = factory.create(ChatterBotType.CLEVERBOT);
				session = bot.createSession();
				message.getChannel().sendMessage(String.format("%s ➤ Clever-Bot session created for the server.", message.getAuthor().getAsMention()));
			}
			
			String input = StringUtils.join(args, " ");
			
			String output = session.think(input);
			message.getChannel().sendMessage("**[CleverBot]** ─ `" + output + "`");
		}
		catch (Exception e)
		{
			message.getChannel().sendMessage("CleverBot has encountered an exception. Resetting CleverBot.");
			factory = null;
		}
	}
}
