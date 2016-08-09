package com.gmail.hexragon.gn4rBot.command.media;

import com.gmail.hexragon.gn4rBot.util.GnarQuotes;
import com.gmail.hexragon.gn4rBot.managers.commands.annotations.Command;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.util.GnarMessage;
import com.gmail.hexragon.gn4rBot.util.MediaCache;

import java.io.File;

@Command(
		aliases = {"getmedia", "getimage", "getshit"},
		description = "Get a png/jpg/gif/vine from GN4R's database.",
		usage = "(id)"
)
public class GetMediaCommand extends CommandExecutor
{
	@Override
	public void execute(GnarMessage message, String[] args)
	{
		if (args.length == 0)
		{
			message.getChannel().sendMessage(String.format("%s ➜ Gotta provide the media ID. `%s`", message.getAuthor().getAsMention(), getCommandManager().getToken() + "listmedia"));
			return;
		}
		
		String greeting = String.format("%s ➜ **%s** Here's your stuff!", message.getAuthor().getAsMention(), GnarQuotes.getRandomQuote());
		
		MediaCache mediaCache = getGnarManager().getServerManager().getMediaCache();
		
		if (mediaCache.getImgFileCache().containsKey(args[0]))
		{
			File file = mediaCache.getImgFileCache().get(args[0]);
			message.getChannel().sendMessage(greeting);
			message.getChannel().sendFile(file, null);
		}
		else if (mediaCache.getGifCache().containsKey(args[0]))
		{
			System.out.println(mediaCache.getGifCache().get(args[0]));
			message.getChannel().sendMessage(greeting + "\n" + mediaCache.getGifCache().get(args[0]));
		}
		else if (mediaCache.getVineCache().containsKey(args[0]))
		{
			message.getChannel().sendMessage(greeting + "\n" + mediaCache.getVineCache().get(args[0]));
		}
		else
		{
			message.getChannel().sendMessage("**" + GnarQuotes.getRandomQuote() + "** I could not find your image. :crying_cat_face:");
		}
	}
}
