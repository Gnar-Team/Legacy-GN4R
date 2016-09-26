package com.gmail.hexragon.gn4rBot.command.admin;

import com.gmail.hexragon.gn4rBot.GnarBot;
import com.gmail.hexragon.gn4rBot.managers.GnarShard;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.managers.commands.annotations.Command;
import com.gmail.hexragon.gn4rBot.util.GnarMessage;
import net.dv8tion.jda.entities.Guild;

import java.util.*;

@Command(
        aliases = {"listservers", "serverlist", "servers"},
        description = "Leaderboard of all of GNAR's servers",
        showInHelp = false
)
public class ListServersCommand extends CommandExecutor {
    @Override
    public void execute(GnarMessage message, String[] args)
    {
        int page = 1;
        try {
            page = Integer.valueOf(args[0]);
        } catch (Exception ignore) {}
        
        if (page == 0) {
            page = 1;
        }
        
        Map<String, Integer> map = new HashMap<>();
        int servers = 0;
        for(GnarShard g : GnarBot.getShards()) {
            servers += g.getJDA().getGuilds().size();
            for (Guild s : g.getJDA().getGuilds()) {
                map.put(s.getName(), s.getUsers().size());
            }
        }



        map = sortByComparator(map, false);
        
        String mb = "";
        
        int pages;
        pages = servers / 10;
        
        int i = 0;
        
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            i++;
            if (i < 10 * page + 1 && i > 10 * page - 10) {
                mb += "**#" + i + "** " + entry.getKey() + " (" + entry.getValue() + " users)\n";
            }
        }
        
        try {
            message.replyRaw("Server Lists (Page " + page + "/" + pages + ")\n" + mb);
        } catch (Exception e) {
            message.replyRaw("An error has occurred, blame Maeyrl for Javacord -> JDA Conversion");
        }
        
    }
    
    private static Map<String, Integer> sortByComparator(Map<String, Integer> unsortMap, final boolean order) {
        
        List<Map.Entry<String, Integer>> list = new LinkedList<>(unsortMap.entrySet());
        
        // Sorting the list based on values
        Collections.sort(list, (o1, o2) ->
        {
            if (order) {
                return o1.getValue().compareTo(o2.getValue());
            } else {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        
        // Maintaining insertion order with the help of LinkedList
        Map<String, Integer> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        
        return sortedMap;
    }
}

