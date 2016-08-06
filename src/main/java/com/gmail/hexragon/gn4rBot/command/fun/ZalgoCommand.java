package com.gmail.hexragon.gn4rBot.command.fun;

import com.gmail.hexragon.gn4rBot.managers.commands.Command;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.util.GnarMessage;
import com.gmail.hexragon.gn4rBot.util.ZalgoGenerator;
import org.apache.commons.lang3.StringUtils;

@Command(
		aliases = {"zalgo"},
		usage = "(query)",
		description = "HE COMES."
)
public class ZalgoCommand extends CommandExecutor
{
	@Override
	public void execute(GnarMessage message, String[] args)
	{
		if (args.length == 0)
		{
			message.reply("Please provide a query.");
			return;
		}
		
		String query = StringUtils.join(args, " ");
		
		message.reply(ZalgoGenerator.process(query, true, true, true));
	}
}
