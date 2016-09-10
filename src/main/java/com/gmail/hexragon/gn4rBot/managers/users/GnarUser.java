package com.gmail.hexragon.gn4rBot.managers.users;

import com.gmail.hexragon.gn4rBot.GnarBot;
import com.gmail.hexragon.gn4rBot.managers.guild.GuildManager;
import net.dv8tion.jda.entities.Role;
import net.dv8tion.jda.entities.User;

public class GnarUser
{
    private final User user;
    private final GuildManager guildManager;
    
    private PermissionLevel permission = PermissionLevel.USER;
    
    GnarUser(GuildManager guildManager, User user)
    {
        this.user = user;
        this.guildManager = guildManager;
        
        if (GnarBot.getAdminIDs().contains(user.getId()))
        {
            setGnarPermission(PermissionLevel.BOT_MASTER);
            return;
        }
        
        if (user == guildManager.getGuild().getOwner())
        {
            setGnarPermission(PermissionLevel.SERVER_OWNER);
        }
        
        for (Role role : guildManager.getGuild().getRolesForUser(user))
        {
            if (role.getName().equals("Bot Commander"))
            {
                setGnarPermission(PermissionLevel.BOT_COMMANDER);
                return;
            }
        }
        
        if (user.isBot()) setGnarPermission(PermissionLevel.BOT);
    }
    
    public PermissionLevel getPermission()
    {
        return permission;
    }
    
    public void setGnarPermission(PermissionLevel permission)
    {
        this.permission = permission;
    }
    
    public User getUser()
    {
        return user;
    }
    
    public GuildManager getGuildManager()
    {
        return guildManager;
    }
}
