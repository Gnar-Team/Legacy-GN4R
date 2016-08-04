package com.gmail.hexragon.gn4rBot.command.fun;

import com.gmail.hexragon.gn4rBot.managers.commands.Command;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.util.ZalgoGenerator;
import net.dv8tion.jda.entities.Message;
import org.apache.commons.lang3.StringUtils;

@Command(
		aliases = {"zalgo"},
		usage = "(query)",
		description = "HE COMES."
)
public class ZalgoCommand extends CommandExecutor
{
	@Override
	public void execute(Message message, String[] args)
	{
		if (args.length == 0)
		{
			message.getChannel().sendMessage(String.format("%s ➜ Please provide a query.", message.getAuthor().getAsMention()));
			return;
		}
		
		String query = StringUtils.join(args, " ");
		
		message.getChannel().sendMessage(ZalgoGenerator.process(query, true, true, true));
	}
}
