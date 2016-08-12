package com.gmail.hexragon.gn4rBot.managers.directMessage;

import com.gmail.hexragon.gn4rBot.GnarBot;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandManager;
import com.gmail.hexragon.gn4rBot.managers.commands.annotations.RequiresGuild;
import com.gmail.hexragon.gn4rBot.managers.guildMessage.GuildCommandManager;
import com.gmail.hexragon.gn4rBot.managers.servers.GnarManager;
import com.gmail.hexragon.gn4rBot.util.GnarMessage;
import com.gmail.hexragon.gn4rBot.util.Utils;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class PMCommandManager extends GuildCommandManager implements CommandManager
{
    PMCommandManager(GnarManager server)
    {
        super(server);
    }
    
    @Override
    public void registerCommand(Class<? extends CommandExecutor> cls) throws IllegalStateException
    {
        if (cls.isAnnotationPresent(RequiresGuild.class))
            throw new IllegalStateException("@NonPrivate annotated classes can't be added to PrivateCommandManager.");
        
        super.registerCommand(cls);
    }
    
    @Override
    public void callCommand(MessageReceivedEvent event)
    {
        String messageContent = event.getMessage().getContent();
        
        if (messageContent.startsWith(token))
        {
            if (messageContent.startsWith(token + "gnar:"))
            {
                messageContent = messageContent.replaceFirst(token + "gnar:", token);
                //directMention = true;
            }
            
            // Splitting sections
            String[] sections = messageContent.split(" ");
            
            String label = sections[0];
            String[] args = Arrays.copyOfRange(sections, 1, sections.length);
            
            GnarMessage gMessage = new GnarMessage(event.getMessage());
            
            for (String regCommand : commandRegistry.keySet())
            {
                if (label.equalsIgnoreCase(token + regCommand))
                {
                    // Calling the command class.
                    CommandExecutor cmd = commandRegistry.get(regCommand);
                    
                    if (getGnarManager().getUserManager() != null && cmd.permissionLevel().value > server.getUserManager().getGnarUser(event.getAuthor()).getPermission().value)
                    {
                        gMessage.reply("You do not have sufficient permission to use this command.");
                        return;
                    }
                    
                    // execute command
                    try
                    {
                        cmd.execute(gMessage, args);
                        requests++;
                    }
                    catch (Exception e)
                    {
                        if (GnarBot.ADMIN_IDS.contains(event.getAuthor().getId()))
                        {
                            event.getAuthor().getPrivateChannel().sendMessage(Utils.exceptionToString(e));
                        }
                        gMessage.reply("An exception occurred while executing this command.");
                        e.printStackTrace();
                    }
                    
                    return;
                }
            }
            
            Map<Integer, String> levenshteinMap = new TreeMap<>();
            
            getCommandRegistry().keySet().forEach(cmd0 -> levenshteinMap.put(StringUtils.getLevenshteinDistance(label, cmd0), cmd0));
            
            List<String> nearest = new ArrayList<>();
            int lowestLev = 5;
            int count = 0;
            
            for (Map.Entry<Integer, String> entry : levenshteinMap.entrySet())
            {
                if (count >= 5)
                {
                    break;
                }
                
                if (entry.getKey() < lowestLev)
                {
                    lowestLev = entry.getKey();
                    nearest.clear();
                    nearest.add(token + entry.getValue());
                    
                    count++;
                }
                else if (entry.getKey() == lowestLev)
                {
                    nearest.add(token + entry.getValue());
                    
                    count++;
                }
            }
            
            gMessage.reply("Invalid command." + (nearest.size() > 0 ? " Nearest commands: `" + Arrays.toString(nearest.toArray()) + "`" : ""));
        }
        else
        {
            getCommand("cbot").execute(new GnarMessage(event.getMessage()), messageContent.split(" "));
        }
    }
}
