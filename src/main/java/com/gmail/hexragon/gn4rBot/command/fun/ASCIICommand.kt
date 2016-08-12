package com.gmail.hexragon.gn4rBot.command.`fun`

import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor
import com.gmail.hexragon.gn4rBot.managers.commands.annotations.Command
import com.gmail.hexragon.gn4rBot.util.GnarMessage
import org.apache.commons.lang3.StringUtils
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.nodes.TextNode

@Command(aliases = arrayOf("ascii"), usage = "(string)", description = "ASCII text art!")
class ASCIICommand : CommandExecutor()
{
    override fun execute(message : GnarMessage, args : Array<String>)
    {
        if (args.size == 0)
        {
            message.reply("Please provide a query.")
            return
        }
        
        try
        {
            val query = StringUtils.join(args, "%20")
            
            val document = Jsoup.connect("http://ascii-text.com/online-ascii-banner-text-generator/slant/$query").get()
            
            val element = document.getElementById("cb").getElementsByTag("pre")[0]
            
            val builder = "```\n${getText(element)}```"
            
            message.replyRaw(builder)
        }
        catch (e : Exception)
        {
            message.reply("Unable to generate ASCII art.")
            e.printStackTrace()
        }
        
    }
    
    private fun getText(cell : Element) : String?
    {
        var text : String? = null
        val childNodes = cell.childNodes()
        if (childNodes.size > 0)
        {
            val childNode = childNodes[0]
            if (childNode is TextNode)
            {
                text = childNode.wholeText
            }
        }
        if (text == null)
        {
            text = cell.text()
        }
        return text
    }
}

