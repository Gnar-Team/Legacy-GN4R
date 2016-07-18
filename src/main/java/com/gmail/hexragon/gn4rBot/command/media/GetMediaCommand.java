package com.gmail.hexragon.gn4rBot.command.media;

import com.gmail.hexragon.gn4rBot.command.misc.GnarQuotes;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandManager;
import com.gmail.hexragon.gn4rBot.util.MediaCache;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

import java.io.File;

public class GetMediaCommand extends CommandExecutor
{
    public GetMediaCommand(CommandManager manager)
    {
        super(manager);
        setDescription("Get a png/jpg/gif/vine from GN4R's database.");
    }

    @Override
    public void execute(MessageReceivedEvent event, String[] args)
    {
        String greeting = "**" + GnarQuotes.getRandomQuote() + "** Here's your stuff!";
    
        MediaCache mediaCache = getGnarGuild().getGuildManager().getMediaCache();
	    
        if (mediaCache.getImgFileCache().containsKey(args[0]))
        {
        	File file = mediaCache.getImgFileCache().get(args[0]);
	        event.getChannel().sendMessage(greeting);
	        event.getChannel().sendFile(file, null);
        }
        else if (mediaCache.getGifCache().containsKey(args[0]))
        {
	        System.out.println(mediaCache.getGifCache().get(args[0]));
	        event.getChannel().sendMessage(greeting + "\n" + mediaCache.getGifCache().get(args[0]));
        }
        else if (mediaCache.getVineCache().containsKey(args[0]))
        {
	        event.getChannel().sendMessage(greeting + "\n" + mediaCache.getVineCache().get(args[0]));
        }
        else
        {
	        event.getChannel().sendMessage("**" + GnarQuotes.getRandomQuote() + "** I could not find your image. :crying_cat_face:");
        }
    }
}
