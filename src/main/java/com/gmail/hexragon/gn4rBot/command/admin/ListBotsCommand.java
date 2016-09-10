package com.gmail.hexragon.gn4rBot.command.admin;

import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.managers.commands.annotations.Command;
import com.gmail.hexragon.gn4rBot.util.GnarMessage;
import net.dv8tion.jda.entities.Guild;
import net.dv8tion.jda.entities.User;

import java.util.ArrayList;
import java.util.Collections;

import static com.gmail.hexragon.gn4rBot.managers.users.PermissionLevel.BOT_MASTER;

@Command(
        aliases = {"listbots", "lbot", "botslist"},
        description = "Displays all of GNARs Bots that he knows",
        permissionRequired = BOT_MASTER,
        showInHelp = false
)
public class ListBotsCommand extends CommandExecutor {
            
    @Override
    public void execute(GnarMessage message, String[] args) {
        
        int page = 1;
        try {
            page = Integer.valueOf(args[0]);
        } catch (NumberFormatException ignore) {}
        
        if (page == 0) { page = 1; }
        
        ArrayList<String> list = new ArrayList<>();
        
        for(Guild s : message.getJDA().getGuilds()) {
            s.getUsers().stream()
                    .filter(User::isBot)
                    .filter(u -> !list.contains(u.getUsername()))
                    .forEach(u -> list.add(u.getUsername()));
        }
        
        Collections.sort(list);
        
        String mb = "";
        
        int pages;
        if(message.getJDA().getGuilds().size() % 10 == 0) {
            pages = list.size()/10;
        } else {
            pages = list.size()/10+1;
        }
        
        int i = 0;
        
        for (String g : list) {
            i++;
            if (i < 10 * page + 1&& i > 10 * page - 10) {
                mb = mb + "**#" + i + "** " + g + "\n";
            }
        }
        
        try {
            message.replyRaw("Bot Lists (Page " + page + "/" + pages + ")\n" + mb);
        } catch (Exception e) {
            message.replyRaw("An error has occurred, blame Maeyrl for Javacord -> JDA Conversion");
        }
    }
    
    
    
}

