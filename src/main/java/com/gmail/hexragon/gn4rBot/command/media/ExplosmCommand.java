package com.gmail.hexragon.gn4rBot.command.media;

import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.managers.commands.annotations.Command;
import com.gmail.hexragon.gn4rBot.util.GnarMessage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.Random;

@Command(
        aliases = {"c&h", "explosm"},
        usage = "[number|latest]",
        description = "Shit-posts from Explosm."
)
public class ExplosmCommand extends CommandExecutor
{
    public ExplosmCommand()
    {
        setDescription("We all need some satire.");
    }
    
    @Override
    public void execute(GnarMessage message, String[] args)
    {
        try
        {
            Document document;
            
            int min = 1500;
            int max = 4300;
            
            String rand;
            
            if (args.length >= 1)
            {
                int input;
                try
                {
                    input = Integer.valueOf(args[0]);
                    
                    if (input > max || input < 100)
                    {
                        message.reply("Explosm does not have a comic for that number.");
                    }
                    
                    rand = String.valueOf(input);
                }
                catch (NumberFormatException e)
                {
                    if (args[0].equalsIgnoreCase("latest"))
                    {
                        rand = "latest";
                    }
                    else
                    {
                        message.reply("You didn't enter a proper number.");
                        return;
                    }
                }
            }
            else
            {
                rand = String.valueOf(min + new Random().nextInt(max - min));
            }
            
            document = Jsoup.connect(String.format("http://explosm.net/comics/%s/", rand)).get();
            
            String builder =
                    "Cyanide and Happiness" + "\n" +
                            "No: **" + rand + "**\n" +
                            "Link: " + document.getElementById("main-comic").absUrl("src");
            
            message.getChannel().sendMessage(builder);
        }
        catch (Exception e)
        {
            message.reply("Unable to grab Cyanide and Happiness comic.");
            e.printStackTrace();
        }
    }
}
