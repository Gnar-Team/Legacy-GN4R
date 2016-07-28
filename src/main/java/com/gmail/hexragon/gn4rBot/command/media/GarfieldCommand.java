package com.gmail.hexragon.gn4rBot.command.media;

import com.gmail.hexragon.gn4rBot.managers.commands.Command;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

@Command(
		aliases = {"garfield"},
		description = "Get some of your favorite fat cat comics!"
)
public class GarfieldCommand extends CommandExecutor
{
	@Override
	public void execute(MessageReceivedEvent event, String[] args)
	{
		try
		{
			Document document;
			
			document = Jsoup.connect("https://garfield.com/comic/").followRedirects(true).get();
			
			String link = document.getElementsByClass("img-responsive").get(0).absUrl("src");
			
			String builder =
					"Garfield" + "\n" +
							"Date: **" + link.substring(link.lastIndexOf("/") + 1, link.lastIndexOf(".")) + "**\n" +
							"Link: " + link;
			event.getChannel().sendMessage(builder);
			
		}
		catch (Exception e)
		{
			event.getChannel().sendMessage(String.format("%s ➤ Unable to grab Garfield comic.", event.getAuthor().getAsMention()));
			e.printStackTrace();
		}
	}
}
