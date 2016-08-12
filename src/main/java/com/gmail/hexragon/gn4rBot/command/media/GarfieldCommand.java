package com.gmail.hexragon.gn4rBot.command.media;

import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.managers.commands.annotations.Command;
import com.gmail.hexragon.gn4rBot.util.GnarMessage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

@Command(
        aliases = "garfield",
        description = "Get some of your favorite fat cat comics!"
)
public class GarfieldCommand extends CommandExecutor
{
    @Override
    public void execute(GnarMessage message, String[] args)
    {
        try
        {
            Document document;
            
            document = Jsoup.connect("https://garfield.com/comic/random").followRedirects(true).get();
            
            String link = document.getElementsByClass("img-responsive").get(0).absUrl("src");
            
            String builder =
                    "Garfield" + "\n" +
                            "Date: **" + link.substring(link.lastIndexOf("/") + 1, link.lastIndexOf(".")) + "**\n" +
                            "Link: " + link;
            message.replyRaw(builder);
            
        }
        catch (Exception e)
        {
            message.reply("Unable to grab Garfield comic.");
            e.printStackTrace();
        }
    }
}
