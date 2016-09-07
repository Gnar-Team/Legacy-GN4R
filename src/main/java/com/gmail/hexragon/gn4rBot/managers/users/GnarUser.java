package com.gmail.hexragon.gn4rBot.managers.users;

import com.gmail.hexragon.gn4rBot.GnarBot;
import com.gmail.hexragon.gn4rBot.managers.GuildManager;
import net.dv8tion.jda.entities.Role;
import net.dv8tion.jda.entities.User;

public class GnarUser
{
    private final User user;
    private final GuildManager gnarGuild;
    
    private PermissionLevel permission = PermissionLevel.USER;
    
    GnarUser(GuildManager gnarGuild, User user)
    {
        this.user = user;
        this.gnarGuild = gnarGuild;
        
        if (GnarBot.ADMIN_IDS.contains(user.getId()))
        {
            setGnarPermission(PermissionLevel.BOT_MASTER);
            return;
        }
        
        if (user == gnarGuild.getGuild().getOwner())
        {
            setGnarPermission(PermissionLevel.SERVER_OWNER);
        }
        
        for (Role role : gnarGuild.getGuild().getRolesForUser(user))
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
    
    public GuildManager getGnarGuild()
    {
        return gnarGuild;
    }
}
