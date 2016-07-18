package com.gmail.hexragon.gn4rBot.command.admin;

import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandManager;
import com.gmail.hexragon.gn4rBot.managers.users.PermissionLevel;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import org.apache.commons.lang3.StringUtils;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class JavascriptCommand extends CommandExecutor
{
	public JavascriptCommand(CommandManager manager)
	{
		super(manager);
		setPermission(PermissionLevel.BOT_MASTER);
		showInHelp(false);
	}
	
	@Override
	public void execute(MessageReceivedEvent event, String[] args)
	{
		ScriptEngine engine = new ScriptEngineManager().getEngineByName("javascript");
		
		engine.put("jda", event.getJDA());
		
		engine.put("messageEvent", event);
		engine.put("message", event.getMessage());
		
		
		engine.put("guild", event.getGuild());
		engine.put("channel", event.getChannel());
		
		
		String script = StringUtils.join(args, " ");
		
		event.getChannel().sendMessage(String.format("%s ➤ Running `%s`.", event.getAuthor().getAsMention(), script));
		
		Object result;
		
		try
		{
			result = engine.eval(script);
		}
		catch (ScriptException e)
		{
			event.getChannel().sendMessage(String.format("%s ➤ The error `%s` occurred while attempting to execute JavaScript.", event.getAuthor().getAsMention(), e.toString()));
			return;
		}
		
		if (result != null)
		{
			if (result.getClass() == Integer.class
					|| result.getClass() == Double.class
					|| result.getClass() == String.class
					|| result.getClass() == Boolean.class)
			{
				event.getChannel().sendMessage(String.format("%s ➤ Result is `%s`.", event.getAuthor().getAsMention(), result));
			}
		}
		//
		
	}
}