package com.gmail.hexragon.gn4rBot.command.games;

import com.gmail.hexragon.gn4rBot.GnarBot;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.managers.commands.annotations.Command;
import com.gmail.hexragon.gn4rBot.util.GnarMessage;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.StringJoiner;

@Command(
        aliases = {"game", "gamelookup"},
        usage = "(query)",
        description = "Look up information about a game."
)
public class GameLookupCommand extends CommandExecutor
{
    @Override
    public void execute(GnarMessage message, String[] args)
    {
        try
        {
            String query = StringUtils.join(args, "+");
            
            HttpResponse<JsonNode> response = Unirest.get("https://videogamesrating.p.mashape.com/get.php?count=5&game=" + query)
                    .header("X-Mashape-Key", GnarBot.getAuthTokens().get("mashape"))
                    .header("Accept", "application/json")
                    .asJson();
            
            JSONArray jsa = response.getBody().getArray();
            
            if (jsa.length() == 0)
            {
                message.getChannel().sendMessage(String.format("%s ➜ No game found with that title.", message.getAuthor().getAsMention()));
                return;
            }
            
            JSONObject jso = new JSONObject(jsa.getJSONObject(0).toString())
            {
                @Override
                public String getString(String key) throws JSONException
                {
                    String s = super.getString(key);
                    return s == null || s.isEmpty() ? "Not found." : s;
                }
            };
            
            StringJoiner joiner = new StringJoiner("\n");
            
            joiner.add("Title: **" + jso.getString("title") + "**");
            joiner.add("Publisher: **" + jso.getString("publisher") + "**");
            joiner.add("Score: **" + jso.getString("score") + "**");
            joiner.add("Thumbnail: " + jso.getString("thumb"));
            joiner.add("Description: **" + jso.getString("short_description") + "**");
            
            message.getChannel().sendMessage(joiner.toString());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
