package com.gmail.hexragon.gn4rBot.managers.guild;

import com.gmail.hexragon.gn4rBot.managers.commands.CommandManager;
import com.gmail.hexragon.gn4rBot.managers.GnarShard;
import com.gmail.hexragon.gn4rBot.managers.users.UserManager;
import com.gmail.hexragon.gn4rBot.util.FileManager;
import com.gmail.hexragon.gn4rBot.util.NullableJSONObject;
import net.dv8tion.jda.entities.Guild;
import net.dv8tion.jda.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import org.json.JSONObject;

import java.io.File;

public class GuildManager extends net.dv8tion.jda.managers.GuildManager
{
    private final String accessID;
    private final UserManager userManager;
    private final CommandManager commandManager;
    private final GnarShard gnarShard;
    
    private final FileManager fileManager;
    private final JSONObject jsonObject;
    
    private final boolean isPrivate;
    
    public GuildManager(String accessID, GnarShard gnarShard, Guild guild, boolean isPrivate)
    {
        super(guild);
        
        this.accessID = accessID;
        this.gnarShard = gnarShard;
        this.isPrivate = isPrivate;
    
        String basePath = String.format("_DATA/servers/%s.json", accessID);
        fileManager = new FileManager(basePath);
        fileManager.createIfNotFound();
    
        String content = fileManager.readText();
        
        if (content.length() == 0) jsonObject = new NullableJSONObject();
        else jsonObject = new NullableJSONObject(content);
        
        saveFile();
    
        if (!isPrivate) this.userManager = new UserManager(this);
        else this.userManager = null;
        
        this.commandManager = new CommandManager(this);
       
        gnarShard.getGlobalCMDRegistry().entrySet().forEach(entry ->
                commandManager.registerCommand(entry.getKey(), entry.getValue()));
    
        gnarShard.getManagerCommandRegistry().forEach(commandManager::registerCommand);
        
        if (!isPrivate) gnarShard.getGuildCommandRegistry().forEach(commandManager::registerCommand);
    }
    
    public CommandManager getCommandManager()
    {
        return commandManager;
    }
    
    
    public UserManager getUserManager()
    {
        return userManager;
    }
    
    
    public String getAccessID()
    {
        return accessID;
    }
    
    
    public GnarShard getGnarShard()
    {
        return gnarShard;
    }
    
    
    public void handleMessageEvent(MessageReceivedEvent event)
    {
        getCommandManager().callCommand(event);
    }
    
    
    public void handleUserJoin(GuildMemberJoinEvent event)
    {
        
    }
    
    
    public void handleUserLeave(GuildMemberLeaveEvent event)
    {
        
    }
    
    
	public File getFile()
	{
		return fileManager.getFile();
	}
    
    public void saveFile()
    {
        fileManager.writeText(jsonObject.toString(4));
    }
    
    public boolean isPrivate()
    {
        return isPrivate;
    }
}