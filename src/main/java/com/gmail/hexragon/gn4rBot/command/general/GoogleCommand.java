package com.gmail.hexragon.gn4rBot.command.general;

import com.gmail.hexragon.gn4rBot.managers.commands.Command;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import net.dv8tion.jda.entities.Message;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.StringJoiner;

@Command(
		aliases = "google",
		usage = "(query)",
		description = "Who needs browsers!?"
)
public class GoogleCommand extends CommandExecutor
{
	@Override
	public void execute(Message message, String[] args)
	{
		if (args.length == 0)
		{
			message.getChannel().sendMessage(String.format("%s ➤ Gotta have a query to Google.", message.getAuthor().getAsMention()));
			return;
		}
		
		try
		{
			String query = StringUtils.join(args, " ");
			Message msg = message.getChannel().sendMessage(String.format("%s ➤ Searching `%s`.", message.getAuthor().getAsMention(), query));
			
			String userAgent = "GN4R-Bot"; // Change this to your company's name and bot homepage!
			
			Elements links = Jsoup.connect(
					String.format("http://www.google.com/search?q=%s", URLEncoder.encode(query, StandardCharsets.UTF_8.displayName())))
					.userAgent(userAgent).get().select(".g>.r>a");
			
			StringJoiner joiner = new StringJoiner("\n");
			
			for (Element link : links)
			{
				String title = link.text();
				String url = link.absUrl("href"); // Google returns URLs in format "http://www.google.com/url?q=<url>&sa=U&ei=<someKey>".
				url = URLDecoder.decode(url.substring(url.indexOf('=') + 1, url.indexOf('&')), StandardCharsets.UTF_8.displayName());
				
				if (!url.startsWith("http"))
				{
					continue; // Ads/news/etc.
				}
				
				joiner.add("Title: **" + title + "**");
				joiner.add("URL: " + url);
				
				break;
			}
			
			
			if (!links.isEmpty()) msg.updateMessage(joiner.toString());
			else msg.updateMessage(String.format("%s ➤ No results for `%s`.", message.getAuthor().getAsMention(), query));
		}
		catch (IOException e)
		{
			message.getChannel().sendMessage(message.getAuthor().getAsMention() + " ➤ Unable to Google stuff.");
			e.printStackTrace();
		}
	}
}



