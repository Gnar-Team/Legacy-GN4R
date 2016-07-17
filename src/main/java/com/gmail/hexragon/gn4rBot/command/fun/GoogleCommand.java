package com.gmail.hexragon.gn4rBot.command.fun;

import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandManager;
import net.dv8tion.jda.entities.Message;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.StringJoiner;

public class GoogleCommand extends CommandExecutor
{
	public GoogleCommand(CommandManager manager)
	{
		super(manager);
		setDescription("Because who needs browsers?");
	}

	@Override
	public void execute(MessageReceivedEvent event, String[] args)
	{
		try
		{
			String query = StringUtils.join(args, " ");
			Message msg = event.getChannel().sendMessage(String.format("%s ➤ Searching `%s`.", event.getAuthor().getAsMention(), query));

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

				joiner.add("Title: **" + title +"**");
				joiner.add("URL: " + url);

				break;
			}


			if (!links.isEmpty()) msg.updateMessage(joiner.toString());
			else msg.updateMessage(String.format("%s ➤ No results for `%s`.", event.getAuthor().getAsMention(), query));
		}
		catch (IOException e)
		{
			event.getChannel().sendMessage(String.format("%s ➤ Unable to Google stuff.", event.getAuthor().getAsMention()));
			e.printStackTrace();
		}
	}
}



