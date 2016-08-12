package com.gmail.hexragon.gn4rBot.command.fun;

import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.managers.commands.annotations.Command;
import com.gmail.hexragon.gn4rBot.util.GnarMessage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import java.util.Random;

@Command(
        aliases = {"rule", "rule34"},
        usage = "[query]",
        description = "Pulls a random rule 34 article from your keywords",
        showInHelp = false
)

public class Rule34Command extends CommandExecutor
{
    
    @Override
    public void execute(GnarMessage message, String[] args)
    {
        String tag = "";
        try
        {
            for (String s : args)
            {
                if (s.equals("/$rule")) continue;
                if (tag.equals(""))
                {
                    tag += ("&tags=" + s);
                }
                else
                {
                    tag += ("+" + s);
                }
            }
        }
        catch (Exception ignore) {}
        
        try
        {
            
            String xml = "http://rule34.xxx/index.php?page=dapi&s=post&q=index" + tag;
            
            Document document = Jsoup.connect(xml).parser(Parser.xmlParser()).get();
            
            Elements posts = document.getElementsByTag("post");
            
            int rand = new Random().nextInt(posts.size());
            
            Element target = posts.get(rand);
            
            String url;
            
            Attributes att = target.attributes();
            
            Attribute att2 = att.asList().get(2);
            
            url = att2.getValue();
            
            message.reply("http:" + url);
        }
        catch (Exception e)
        {
            message.reply("Please refer to rule 35.");
        }
        
        
    }
}
