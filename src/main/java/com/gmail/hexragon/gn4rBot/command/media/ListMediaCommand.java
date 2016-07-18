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
    }

    @Override
    public void execute(MessageReceivedEvent event, String[] args)
    {
        String greeting = "**" + GnarQuotes.getRandomQuote() + "** Wanna look at my stash?";
    
        MediaCache mediaCache = getGnarGuild().getGuildManager().getMediaCache();
	    StringBuilder builder = new StringBuilder();
	    
	    builder.append("```xl\n[IMAGES]\n");
	    mediaCache.getImgCache().keySet().forEach(s -> builder.append(" - ").append(s).append("\n"));
	    builder.append("```\n");
	
	    builder.append("```xl\n[GIFS]\n");
	    mediaCache.getGifCache().keySet().forEach(s -> builder.append(" - ").append(s).append("\n"));
	    builder.append("```\n");
	    
	    builder.append("```xl\n[VINES]\n");
	    mediaCache.getVineCache().keySet().forEach(s -> builder.append(" - ").append(s).append("\n"));
	    builder.append("```\n");
	    
	    event.getChannel().sendMessage(greeting + builder.toString());
    }
}
