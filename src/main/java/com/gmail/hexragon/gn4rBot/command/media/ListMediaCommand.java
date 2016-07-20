package com.gmail.hexragon.gn4rBot.command.media;

import com.gmail.hexragon.gn4rBot.command.misc.GnarQuotes;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandManager;
import com.gmail.hexragon.gn4rBot.util.MediaCache;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

public class ListMediaCommand extends CommandExecutor
{
	public ListMediaCommand(CommandManager manager)
	{
		super(manager);
		setDescription("List all media from GN4R's database.");
		setUsage("listmedia (img|gif|vine|all)");
	}
	
	@Override
	public void execute(MessageReceivedEvent event, String[] args)
	{
		if (args.length == 0)
		{
			event.getChannel().sendMessage(String.format("%s ➤ What kind of media? `[img, gif, vine, all]`", event.getAuthor().getAsMention()));
			return;
		}
		
		String greeting = String.format("%s ➤ **%s** Here's what I got in my stash!", event.getAuthor().getAsMention(), GnarQuotes.getRandomQuote());
		
		MediaCache mediaCache = getGnarGuild().getGuildManager().getMediaCache();
		StringBuilder builder = new StringBuilder();
		
		switch (args[0])
		{
			case "img":
				builder.append("```xl\n[IMAGES]\n");
				mediaCache.getImgCache().keySet().forEach(s -> builder.append(" - ").append(s).append("\n"));
				builder.append("```\n");
				break;
			
			case "gif":
				builder.append("```xl\n[GIFS]\n");
				mediaCache.getGifCache().keySet().forEach(s -> builder.append(" - ").append(s).append("\n"));
				builder.append("```\n");
				break;
			
			case "vine":
				builder.append("```xl\n[VINES]\n");
				mediaCache.getVineCache().keySet().forEach(s -> builder.append(" - ").append(s).append("\n"));
				builder.append("```\n");
				break;
			
			case "all":
				builder.append("```xl\n[IMAGES]\n");
				mediaCache.getImgCache().keySet().forEach(s -> builder.append(" - ").append(s).append("\n"));
				builder.append("```\n");
				builder.append("```xl\n[GIFS]\n");
				mediaCache.getGifCache().keySet().forEach(s -> builder.append(" - ").append(s).append("\n"));
				builder.append("```\n");
				builder.append("```xl\n[VINES]\n");
				mediaCache.getVineCache().keySet().forEach(s -> builder.append(" - ").append(s).append("\n"));
				builder.append("```\n");
				break;
			
			default:
				event.getChannel().sendMessage(String.format("%s ➤ Invalid media type. `[img, gif, vine, all]`", event.getAuthor().getAsMention()));
		}
		
		event.getAuthor().getPrivateChannel().sendMessage(greeting + builder.toString());
		
		event.getChannel().sendMessage(String.format("%s ➤ **" + GnarQuotes.getRandomQuote() + "** Sent you my list of available media.", event.getAuthor().getAsMention()));
	}
}
