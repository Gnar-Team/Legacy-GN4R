package com.gmail.hexragon.gn4rBot.managers.commands;

import com.gmail.hexragon.gn4rBot.GnarBot;
import com.gmail.hexragon.gn4rBot.managers.guild.GuildManager;
import com.gmail.hexragon.gn4rBot.util.GnarMessage;
import com.gmail.hexragon.gn4rBot.util.Utils;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

import java.util.Arrays;

public class CommandManager extends CommandRegistry
{
    private final GuildManager server;
    
    private int requests = 0;
    
    private String token = "_"; //default token
    
    public CommandManager(GuildManager server)
    {
        this.server = server;
    }
    
    public String getToken()
    {
        return token;
    }
    
    public void setToken(String token)
    {
        this.token = token;
    }
    
    public GuildManager getGuildManager()
    {
        return server;
    }
    
    public void callCommand(MessageReceivedEvent event)
    {
        //boolean directMention = false;
        
        String messageContent = event.getMessage().getContent();
        
        if (messageContent.startsWith(token))
        {
            if (messageContent.startsWith(token + "gnar:"))
            {
                messageContent = messageContent.replaceFirst(token + "gnar:", token);
            }
            
            // Splitting sections
            String[] sections = messageContent.split(" ");
            
            String label = sections[0];
            String[] args = Arrays.copyOfRange(sections, 1, sections.length);
            
            GnarMessage gMessage = new GnarMessage(event.getMessage());
            
            for (String regCommand : getRegistry().keySet())
            {
                if (label.equalsIgnoreCase(token + regCommand))
                {
                    // Calling the command class.
                    CommandExecutor cmd = getRegistry().get(regCommand);
                    
                    if (getGuildManager().getUserManager() != null && cmd.permissionLevel().value > server.getUserManager().getGnarUser(event.getAuthor()).getPermission().value)
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
                    catch (RuntimeException e)
                    {
                        if (GnarBot.getAdminIDs().contains(event.getAuthor().getId()))
                        {
                            try
                            {
                                event.getAuthor().getPrivateChannel().sendMessage(Utils.exceptionToString(e));
                            }
                            catch (UnsupportedOperationException ignored) // usually message too long
                            {
                                event.getAuthor().getPrivateChannel().sendMessage("Error that occured was too long for Discord.");
                            }
                        }
                        gMessage.reply("An exception occurred while executing this command.");
                        e.printStackTrace();
                    }
                    
                    return;
                }
            }
        }
    }
    
    public int getRequests()
    {
        return requests;
    }
}
