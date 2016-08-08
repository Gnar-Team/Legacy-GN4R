package com.gmail.hexragon.gn4rBot.util;

import com.gmail.hexragon.gn4rBot.GnarBot;
import net.dv8tion.jda.JDA;
import org.json.JSONObject;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class DiscordBotsInfo {

    public static void updateServerCount(JDA api) {
        try {
            String url= GnarBot.TOKENS.get("url");
            URL object=new URL(url);

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

            System.out.println("Successfully updated server count to " + api.getGuilds().size() + ", Response Code: " + con.getResponseCode());

        } catch (Exception e) { System.out.println("Error updating server count..."); e.printStackTrace();}
    }
}
