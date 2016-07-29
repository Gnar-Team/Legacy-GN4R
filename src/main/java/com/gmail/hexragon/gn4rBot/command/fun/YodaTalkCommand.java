package com.gmail.hexragon.gn4rBot.command.fun;

import com.gmail.hexragon.gn4rBot.managers.commands.Command;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import net.dv8tion.jda.entities.Message;
import org.apache.commons.lang3.StringUtils;

@Command(
		aliases = {"yodatalk"},
		usage = "(sentence)",
		description = "Learn to speak like Yoda, you will."
)
public class YodaTalkCommand extends CommandExecutor
{
	@Override
	public void execute(Message message, String[] args)
	{
		if (args.length == 0)
		{
			message.getChannel().sendMessage(String.format("%s ➤ At least put something. `=[`", message.getAuthor().getAsMention()));
			return;
		}
		
		try
		{
			String query = StringUtils.join(args, "+");
			
			HttpResponse<String> response = Unirest.get("https://yoda.p.mashape.com/yoda?sentence=" + query)
					.header("X-Mashape-Key", "dw1mYrC2ssmsh2WkFGHaherCtl48p1wtuHWjsnYbP3Y7q8y6M5")
					.header("Accept", "text/plain")
					.asString();
			
			String result = response.getBody();
			
			message.getChannel().sendMessage(result);
		}
		catch (UnirestException e)
		{
			e.printStackTrace();
		}
	}
}
