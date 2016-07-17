package com.gmail.hexragon.gn4rBot.command.media;

import com.gmail.hexragon.gn4rBot.command.misc.GnarQuotes;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandManager;
import com.gmail.hexragon.gn4rBot.util.MediaCache;
import net.dv8tion.jda.MessageBuilder;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

import java.io.File;

public class GetMediaCommand extends CommandExecutor
{
    public GetMediaCommand(CommandManager manager)
    {
        super(manager);
        setUsage("getMedia");
    }

    @Override
    public void execute(MessageReceivedEvent event, String[] args)
    {
        File f;

        MessageBuilder mb = new MessageBuilder();
        mb.appendString("**" + GnarQuotes.getRandomQuote() + "** Here's your image!");

        if(MediaCache.imageCache.containsKey(args[0])) {
            f = MediaCache.imageCache.get(args[0]);
            event.getChannel().sendFile(f, mb.build());
        } else if (MediaCache.gifCache.containsKey(args[0])){
            event.getChannel().sendMessage(mb.build() + "\n" + MediaCache.gifCache.get(args[0]));
        } else if (MediaCache.vineCache.containsKey(args[0])){
            event.getChannel().sendMessage(mb.build() + "\n" + MediaCache.vineCache.get(args[0]));
        } else {
            event.getChannel().sendMessage("**" + GnarQuotes.getRandomQuote() + "** I could not find your image. :crying_cat_face:");
        }
    }
}
