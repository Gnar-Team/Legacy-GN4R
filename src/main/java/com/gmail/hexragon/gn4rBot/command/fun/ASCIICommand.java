package com.gmail.hexragon.gn4rBot.command.fun;

import com.gmail.hexragon.gn4rBot.managers.commands.Command;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import net.dv8tion.jda.entities.Message;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

import java.util.List;

@Command(
		aliases = {"ascii"},
		usage = "(string)",
		description = "ASCII text art!"
)
public class ASCIICommand extends CommandExecutor
{
	@Override
	public void execute(Message message, String[] args)
	{
		if (args.length == 0)
		{
			message.getChannel().sendMessage(String.format("%s ➜ Please provide a query.", message.getAuthor().getAsMention()));
			return;
		}
		
		try
		{
			String query = StringUtils.join(args, "%20");
			
			Document document;
			
			document = Jsoup.connect("http://ascii-text.com/online-ascii-banner-text-generator/slant/"+query).get();
			
			Element element = document.getElementById("cb").getElementsByTag("pre").get(0);
			
			String builder = "```\n" + getText(element) + "```";
			
			message.getChannel().sendMessage(builder);
			
		}
		catch (Exception e)
		{
			message.getChannel().sendMessage(String.format("%s ➜ Unable to generate ASCII banner.", message.getAuthor().getAsMention()));
			e.printStackTrace();
		}
	}
	
	private String getText(Element cell) {
		String text = null;
		List<Node> childNodes = cell.childNodes();
		if (childNodes.size() > 0) {
			Node childNode = childNodes.get(0);
			if (childNode instanceof TextNode) {
				text = ((TextNode)childNode).getWholeText();
			}
		}
		if (text == null) {
			text = cell.text();
		}
		return text;
	}
}
