package com.gmail.hexragon.gn4rBot.testing

import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor
import com.gmail.hexragon.gn4rBot.managers.commands.CommandManager
import com.gmail.hexragon.gn4rBot.util.Utils
import net.dv8tion.jda.events.message.MessageReceivedEvent
import org.json.JSONObject
import java.util.*

class KOTLIN_xkcdCommand(cm : CommandManager) : CommandExecutor(cm)
{
	init
	{
		description = "Kotlin XKCD test command."
		showInHelp(false)
	}
	
	override fun execute(event : MessageReceivedEvent?, args : Array<out String>?)
	{
		try
		{
			val latestJSON : JSONObject? = Utils.readJsonFromUrl("http://xkcd.com/info.0.json")

			val min = 500
			val max : Int = latestJSON?.get("num") as Int

			val rand = Random().nextInt(max - min)

			val randJSON : JSONObject? = Utils.readJsonFromUrl("http://xkcd.com/$rand/info.0.json")

			val joiner = StringJoiner("\n")

			joiner.add("XKCD **${randJSON?.get("title")}**")
			joiner.add("No: **${randJSON?.get("num")}**")
			joiner.add("Link: ${randJSON?.get("img")}")

			event?.channel?.sendMessage(joiner.toString())
		}
		catch(e : Exception)
		{
			event?.channel?.sendMessage("${event.author.asMention} ➤ Unable to grab xkcd comic.")
			e.printStackTrace()
		}
	}
}