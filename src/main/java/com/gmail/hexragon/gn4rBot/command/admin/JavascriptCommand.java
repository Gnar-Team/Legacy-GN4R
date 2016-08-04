package com.gmail.hexragon.gn4rBot.command.admin;

import com.gmail.hexragon.gn4rBot.managers.commands.Command;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.managers.users.PermissionLevel;
import com.gmail.hexragon.gn4rBot.util.GnarMessage;
import org.apache.commons.lang3.StringUtils;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

@Command(
		aliases = "runjs",
		description = "Run JavaScript commands.",
		permissionRequired = PermissionLevel.BOT_MASTER,
		showInHelp = false
)
public class JavascriptCommand extends CommandExecutor
{
	@Override
	public void execute(GnarMessage message, String[] args)
	{
		ScriptEngine engine = new ScriptEngineManager().getEngineByName("javascript");
		
		engine.put("jda", message.getJDA());
		
		engine.put("message", message);
		
		engine.put("guild", getGnarGuild().getGuild());
		engine.put("channel", message.getChannel());
		
		String script = StringUtils.join(args, " ");
		
		message.reply("Running `"+script+"`.");
		
		Object result;
		
		try
		{
			result = engine.eval(script);
		}
		catch (ScriptException e)
		{
			message.reply("The error `"+e.toString()+"` occurred while executing the JavaScript statement.");
			return;
		}
		
		if (result != null)
		{
			if (result.getClass() == Integer.class
					|| result.getClass() == Double.class
					|| result.getClass() == String.class
					|| result.getClass() == Boolean.class)
			{
				message.reply("The result is `"+result+"`.");
			}
		}
	}
}