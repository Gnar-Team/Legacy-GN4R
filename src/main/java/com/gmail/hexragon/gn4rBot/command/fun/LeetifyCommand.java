package com.gmail.hexragon.gn4rBot.command.fun;

import com.gmail.hexragon.gn4rBot.managers.commands.Command;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import net.dv8tion.jda.entities.Message;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Command(
		aliases = {"leet"},
		usage = "(string)",
		description = "Leet it!"
)
public class LeetifyCommand extends CommandExecutor
{
	private static final Map<String, String> substitutions = new HashMap<String, String>() {{
		put("a", "@");
		put("e", "3");
		put("l", "1");
		put("s", "\\$");
		put("o", "0");
		put("t", "7");
		put("i", "!");
	}};
	
	@Override
	public void execute(Message message, String[] args)
	{
		String s = StringUtils.join(args, " ");
		
		for (Map.Entry<String, String> entry : substitutions.entrySet())
		{
			s = s.replaceAll(entry.getKey(), entry.getValue());
		}
		
		message.getChannel().sendMessage(message.getAuthor().getAsMention() + " ➜ `" + s + "`.");
	}
}
