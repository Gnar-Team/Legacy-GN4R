package com.gmail.hexragon.gn4rBot;

import com.gmail.hexragon.gn4rBot.managers.ServerManager;
import com.gmail.hexragon.gn4rBot.util.DiscordBotsInfo;
import com.gmail.hexragon.gn4rBot.util.FileManager;
import com.gmail.hexragon.gn4rBot.util.PropertiesManager;
import net.dv8tion.jda.JDA;
import net.dv8tion.jda.JDABuilder;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class GnarBot
{
    public static void main(String[] args)
    {
        File dataFolder = new File("_DATA");
        if (!dataFolder.exists())
        {
            System.out.println("[ERROR] - Folder '_DATA' not found.");
            System.exit(1);
            return;
        }
        
        instance = new GnarBot(TOKENS.get("beta-bot"), 4);
    }
    
    private static GnarBot instance;
    private static List<JDA> shards = new ArrayList<>();
    
    private static final long START_TIME = System.currentTimeMillis();
    public static final List<String> ADMIN_IDS = new FileManager("_DATA/administrators").readLines();
    public static final PropertiesManager TOKENS = new PropertiesManager().load(new File("_DATA/tokens.properties"));
    public static final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    
    private GnarBot(String token, int shards)
    {
        try
        {
            int servers = 0;

            for(int id = 0; id < shards; id++)
            {
                JDA jda = new JDABuilder()
                        .useSharding(id, shards)
                        .setBotToken(token)
                        .buildBlocking();

                jda.getAccountManager().setUsername("GNAR");
                jda.getAccountManager().setGame("_help | _invite");
                jda.getAccountManager().update();

                jda.setAutoReconnect(true);
                
                new ServerManager(jda, id);
    
                servers += jda.getGuilds().size();
    
                GnarBot.getShards().add(jda);
            }

            DiscordBotsInfo.updateServerCount(servers);
        }
        catch (LoginException | InterruptedException e)
        {
            e.printStackTrace();
        }
    }
    
    public static GnarBot getInstance()
    {
        return instance;
    }
    
    public static List<JDA> getShards()
    {
        return shards;
    }
    
    public static String getUptimeStamp()
    {
        long seconds = (new Date().getTime() - START_TIME) / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;
        return days + " days, " + hours % 24 + " hours, " + minutes % 60 + " minutes and " + seconds % 60 + " seconds";
    }
    
    public static String getShortUptimeStamp()
    {
        long seconds = (new Date().getTime() - START_TIME) / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;
        return days + "d " + hours % 24 + "h " + minutes % 60 + "m " + seconds % 60 + "s";
    }

    public static int getGuildCount()
    {
        return getShards().stream()
                .mapToInt(jda -> jda.getGuilds().size())
                .sum();
    }
}
