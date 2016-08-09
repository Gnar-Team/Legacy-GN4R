package com.gmail.hexragon.gn4rBot.command.media;

import com.gmail.hexragon.gn4rBot.managers.commands.annotations.Command;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.util.GnarMessage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

@Command(
		aliases = {"rcg"},
		description = "Random shit-post generation!"
)
public class ExplosmRCGCommand extends CommandExecutor
{
	@Override
	public void execute(GnarMessage message, String[] args)
	{
		try
		{
			Document document;
			
			document = Jsoup.connect("http://explosm.net/rcg").get();
			
			Element element = document.getElementById("rcg-comic").getElementsByTag("img").get(0);
			
			String builder =
					"Cyanide and Happiness" + "\n" +
							"**Random Comic Generator**\n" +
							"Link: " + element.absUrl("src");
			
			message.replyRaw(builder);
			
		}
		catch (Exception e)
		{
			message.reply("Unable to grab random Cyanide and Happiness comic.");
			e.printStackTrace();
		}
	}
}
