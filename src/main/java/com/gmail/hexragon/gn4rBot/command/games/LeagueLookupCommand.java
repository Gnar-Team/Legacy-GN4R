package com.gmail.hexragon.gn4rBot.command.games;

import com.gmail.hexragon.gn4rBot.GnarBot;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.managers.commands.annotations.Command;
import com.gmail.hexragon.gn4rBot.util.GnarMessage;
import com.gmail.hexragon.gn4rBot.util.Utils;
import net.dv8tion.jda.MessageBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

@Command(
        aliases = {"league", "lol"},
        usage = "(LOL Username)",
        description = "Look up Leauge of Legends statistics of a player."
)
public class LeagueLookupCommand extends CommandExecutor
{
    @Override
    public void execute(GnarMessage message, String[] args)
    {
        MessageBuilder mb = new MessageBuilder();
        try
        {
            JSONObject jso = Utils.readJsonFromUrl("https://na.api.pvp.net/api/lol/na/v1.4/summoner/by-name/"
                    + args[0] + "?api_key=" + GnarBot.getAuthTokens().get("leauge"));
            JSONObject info = jso.getJSONObject(args[0]);
            mb.appendString("**League Of Legends** Account Info: \n");
            mb.appendString("Season: **SEASON 2016** \n\n");
            mb.appendString("Current Name: **" + info.get("name") + "**\n");
            mb.appendString("ID: **" + info.get("id") + "**\n");
            mb.appendString("Level: **" + info.get("summonerLevel") + "**\n");
            mb.appendString("Icon ID: **" + info.get("profileIconId") + "**\n");
            
            try
            {
                JSONObject jso2 = Utils.readJsonFromUrl(
                        "https://na.api.pvp.net/api/lol/na/v1.3/stats/by-summoner/" + info.get("id")
                                + "/summary?season=SEASON2016&api_key=RGAPI-4A230644-F1D8-40B7-A81E-4566E145BA1C");
                JSONArray j = jso2.getJSONArray("playerStatSummaries");
                JSONObject fin = j.getJSONObject(0);
                
                mb.appendString("Wins: **" + fin.get("wins") + "**\n");
                
                JSONObject stats = (JSONObject) fin.get("aggregatedStats");
                
                mb.appendString("Champion Kills: **" + stats.get("totalChampionKills") + "**\n");
                mb.appendString("Minion Kills: **" + stats.get("totalMinionKills") + "**\n");
                mb.appendString("Assists: **" + stats.get("totalAssists") + "**\n");
                mb.appendString("Neutral Minion Kills: **" + stats.get("totalNeutralMinionsKilled") + "**\n");
                mb.appendString("Turrets Killed: **" + stats.get("totalTurretsKilled") + "**\n");
                
                message.getChannel().sendMessage(mb.build());
            }
            catch (Exception e)
            {
                message.getChannel()
                        .sendMessage("Account not found or there was an error connecting to the LoL API\n"
                                + "Here is what I was able to get: \n");
                try
                {
                    message.getChannel().sendMessage(mb.build());
                }
                catch (Exception ex) {e.printStackTrace();}
                //e.printStackTrace();
            }
        }
        catch (Exception e)
        {
            message.getChannel()
                    .sendMessage("Account not found or there was an error connecting to the LoL API\n"
                            + "Here is what I was able to get: \n");
            try
            {
                message.getChannel().sendMessage(mb.build());
            }
            catch (Exception ex) {
                //e.printStackTrace();
            }
                
            //e.printStackTrace();
        }
    }
}
