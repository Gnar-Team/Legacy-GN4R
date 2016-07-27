package com.gmail.hexragon.gn4rBot.command.games;

import com.gmail.hexragon.gn4rBot.GnarBot;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandManager;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.StringJoiner;

public class GameLookupCommand extends CommandExecutor
{
    public GameLookupCommand(CommandManager manager)
    {
        super(manager);
	    setDescription("Look up information about a game.");
        setUsage("gamelookup (query)");
    }

    @Override
    public void execute(MessageReceivedEvent event, String[] args)
    {
        try
        {
            String query = StringUtils.join(args, "+");
            
            HttpResponse<JsonNode> response = Unirest.get("https://videogamesrating.p.mashape.com/get.php?count=5&game="+query)
                    .header("X-Mashape-Key", GnarBot.TOKENS.get("mashape"))
                    .header("Accept", "application/json")
                    .asJson();
    
            JSONArray jsa = response.getBody().getArray();
            
            if (jsa.length() == 0)
            {
                event.getChannel().sendMessage(String.format("%s ➤ No game found with that title.", event.getAuthor().getAsMention()));
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
            joiner.add("Description: **" + jso.getString("short_description") +"**");
            
            event.getChannel().sendMessage(joiner.toString());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
