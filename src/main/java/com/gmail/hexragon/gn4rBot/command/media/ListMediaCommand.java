package com.gmail.hexragon.gn4rBot.command.media;

import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.managers.commands.annotations.Command;
import com.gmail.hexragon.gn4rBot.util.GnarMessage;
import com.gmail.hexragon.gn4rBot.util.GnarQuotes;
import com.gmail.hexragon.gn4rBot.util.MediaCache;

@Command(
        aliases = {"listmedia", "listimage", "listshit"},
        description = "List all media from GN4R's database.",
        usage = "(img|gif|vine)"
)
public class ListMediaCommand extends CommandExecutor
{
    @Override
    public void execute(GnarMessage message, String[] args)
    {
        if (args.length == 0)
        {
            message.reply("What kind of media? `[img, gif, vine, all]`");
            return;
        }
        
        String greeting = String.format("%s ➜ **%s** Here's what I got in my stash!", message.getAuthor().getAsMention(), GnarQuotes.getRandomQuote());
        
        MediaCache mediaCache = getGuildManager().getGnarShard().getMediaCache();
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
                message.reply("Invalid media type. `[img, gif, vine, all]`");
        }
        
        message.getAuthor().getPrivateChannel().sendMessage(greeting + builder.toString());
        
        message.reply("**" + GnarQuotes.getRandomQuote() + "** Sent you my list of available media.");
    }
}
