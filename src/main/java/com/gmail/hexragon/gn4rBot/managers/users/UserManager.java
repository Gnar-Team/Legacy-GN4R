package com.gmail.hexragon.gn4rBot.managers.users;

import com.gmail.hexragon.gn4rBot.managers.GuildManager;
import net.dv8tion.jda.entities.User;

import java.util.HashMap;
import java.util.Map;

public class UserManager
{
    private final GuildManager server;
    
    private final Map<User, GnarUser> userRegistry;
    
    public UserManager(GuildManager server)
    {
        this.server = server;
        userRegistry = new HashMap<>();
    }
    
    public Map<User, GnarUser> getUserRegistry()
    {
        return userRegistry;
    }
    
    public GnarUser getGnarUser(User user)
    {
        GnarUser aUser = getUserRegistry().get(user);
        if (aUser == null)
        {
            aUser = new GnarUser(server, user);
            getUserRegistry().put(user, aUser);
        }
        
        return aUser;
    }
}
