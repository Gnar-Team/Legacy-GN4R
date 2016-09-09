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
    private static GnarBot instance;
    private static ServerManager serverManager;
    
    public static final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    
    public static final List<String> ADMIN_IDS = new FileManager("_DATA/administrators").readLines();
    public static final PropertiesManager TOKENS = new PropertiesManager().load(new File("_DATA/tokens.properties"));
    private static final long START_TIME = System.currentTimeMillis();

    public static ArrayList<JDA> jdas = new ArrayList<>();
    
    public static void main(String[] args) throws Exception
    {
        File dataFolder = new File("_DATA");
        if (!dataFolder.exists())
        {
            System.out.println("[ERROR] - Folder '_DATA' not found.");
            return;
        }

        instance = new GnarBot(TOKENS.get("main-bot"), 4);
    }
    
    private GnarBot(String token, int shards)
    {
        try
        {
            int servers = 0;

            for(int shard=0; shard<shards; shard++) {
                JDA jda = new JDABuilder().useSharding(shard, shards).setBotToken(token).buildBlocking();

                jda.getAccountManager().setUsername("GNAR");
                jda.getAccountManager().setGame("_help | _invite");
                jda.getAccountManager().update();

                jda.setAutoReconnect(true);

                servers+=jda.getGuilds().size();

                jdas.add(jda);

                serverManager = new ServerManager(jda);
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
    
    public ServerManager getServerManager()
    {
        return serverManager;
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

    public static int getServerCount() {
        int i = 0;
        for(JDA jda : jdas) {
            i += jda.getGuilds().size();
        }

        return i;
    }
}
