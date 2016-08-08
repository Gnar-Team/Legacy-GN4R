package com.gmail.hexragon.gn4rBot.command.admin

import com.gmail.hexragon.gn4rBot.managers.commands.Command
import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor
import com.gmail.hexragon.gn4rBot.managers.users.PermissionLevel
import com.gmail.hexragon.gn4rBot.util.GnarMessage
import org.apache.commons.lang3.StringUtils
import javax.script.ScriptEngineManager
import javax.script.ScriptException

@Command(
		aliases = arrayOf("runjs"),
		description = "Run JavaScript commands.",
		permissionRequired = PermissionLevel.BOT_MASTER,
		showInHelp = false
)
class JavascriptCommand : CommandExecutor()
{
	override fun execute(message : GnarMessage?, args : Array<out String>?)
	{
		val engine = ScriptEngineManager().getEngineByName("javascript")
		
		engine.put("jda", message?.jda)
		engine.put("message", message)
		engine.put("guild", gnarGuild.guild)
		engine.put("channel", message?.channel)

		
		val script = StringUtils.join(args, " ")
		
		message?.reply("Running `$script`.")
		
		val result : Any?
		
		try
		{
			result = engine.eval(script)
		}
		catch (e : ScriptException)
		{
			message?.reply("The error `${e.toString()}` occurred while executing the JavaScript statement.")
			return
		}
		
		if (result != null)
		{
			if (result.javaClass == Int::class.java
					|| result.javaClass == Double::class.java
					|| result.javaClass == String::class.java
					|| result.javaClass == Boolean::class.java)
			{
				message?.reply("The result is `$result`.")
			}
		}
	}
}