package com.gmail.hexragon.gn4rBot.command.fun;

import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandManager;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import org.apache.commons.lang3.StringUtils;

public class YodaTalkCommand extends CommandExecutor
{
	public YodaTalkCommand(CommandManager manager)
	{
		super(manager);
		setDescription("Learn to speak like yoda, you will.");
		setUsage("yodatalk (sentence)");
	}
	
	@Override
	public void execute(MessageReceivedEvent event, String[] args)
	{
		try
		{
			String sentence = StringUtils.join(args, "+");
			
			HttpResponse<String> response = Unirest.get("https://yoda.p.mashape.com/yoda?sentence=" + sentence)
					.header("X-Mashape-Key", "dw1mYrC2ssmsh2WkFGHaherCtl48p1wtuHWjsnYbP3Y7q8y6M5")
					.header("Accept", "text/plain")
					.asString();
			
			String result = response.getBody();
			
			event.getChannel().sendMessage(result);
		}
		catch (UnirestException e)
		{
			e.printStackTrace();
		}
	}
}
