package com.gmail.hexragon.gn4rBot.util;

import com.gmail.hexragon.gn4rBot.GnarBot;
import net.dv8tion.jda.JDA;
import org.json.JSONObject;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class DiscordBotsInfo
{
    
    public static void updateServerCount(JDA api)
    {
        try
        {
            /*


                bots.discord.pw


             */
            String url = GnarBot.TOKENS.get("url");
            URL object = new URL(url);
            
            String authToken = GnarBot.TOKENS.get("authToken");
            
            JSONObject serverCount = new JSONObject();
            
            serverCount.put("server_count", api.getGuilds().size());
            
            HttpURLConnection con = (HttpURLConnection) object.openConnection();
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.24 Safari/537.36");
            con.setRequestProperty("Authorization", authToken);
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestMethod("POST");
            
            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
            wr.write(serverCount.toString());
            wr.flush();
            
            System.out.println("Successfully updated Abal server count to " + api.getGuilds().size() + ", Response Code: " + con.getResponseCode());

            /*


                Carbonitex


             */

            JSONObject serverCount2 = new JSONObject();

            String key = GnarBot.TOKENS.get("serverKey");


            serverCount2.put("key", key);
            serverCount2.put("servercount", api.getGuilds().size());

            object = new URL("https://www.carbonitex.net/discord/data/botdata.php");

            HttpURLConnection conn = (HttpURLConnection) object.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.24 Safari/537.36");
            conn.setRequestProperty("Authorization", authToken);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestMethod("POST");

            OutputStreamWriter wre = new OutputStreamWriter(conn.getOutputStream());
            wre.write(serverCount2.toString());
            wre.flush();

            System.out.println("Successfully updated Carbonitex server count to " + api.getGuilds().size() + ", Response Code: " + conn.getResponseCode());

        }
        catch (Exception e)
        {
            System.out.println("Error updating server count...");
            e.printStackTrace();
        }
    }
}
