package com.gmail.hexragon.gn4rBot.command.fun;

import com.gmail.hexragon.gn4rBot.GnarBot;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandManager;
import com.gmail.hexragon.gn4rBot.util.Utils;
import net.dv8tion.jda.MessageBuilder;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import org.json.JSONArray;
import org.json.JSONObject;

public class LeagueLookupCommand extends CommandExecutor
{
    public LeagueLookupCommand(CommandManager manager)
    {
        super(manager);
        setDescription("Look up Leauge of Legends statistics.");
        setUsage("leauge (LoL username)");
        showInHelp(true);
    }

    @Override
    public void execute(MessageReceivedEvent event, String[] args)
    {
        MessageBuilder mb = new MessageBuilder();
        try {
            JSONObject jso = Utils.readJsonFromUrl("https://na.api.pvp.net/api/lol/na/v1.4/summoner/by-name/"
                    + args[0] + "?api_key=" + GnarBot.TOKENS.get("leauge"));
            JSONObject info = jso.getJSONObject(args[0]);
            mb.appendString("League Of Legends Account Info: \n")
                    .appendString("Season: SEASON2016 \n\n")
                    .appendString("Current Name: " + info.get("name") + "\n")
                    .appendString("ID: " + info.get("id") + "\n")
                    .appendString("Level: " + info.get("summonerLevel") + "\n")
                    .appendString("Icon ID: " + info.get("profileIconId") + "\n");

            try {
                JSONObject jso2 = Utils.readJsonFromUrl(
                        "https://na.api.pvp.net/api/lol/na/v1.3/stats/by-summoner/" + info.get("id")
                                + "/summary?season=SEASON2016&api_key=RGAPI-4A230644-F1D8-40B7-A81E-4566E145BA1C");
                JSONArray j = jso2.getJSONArray("playerStatSummaries");
                JSONObject fin = j.getJSONObject(0);

                mb.appendString("Wins: " + fin.get("wins"));

                JSONObject stats = (JSONObject) fin.get("aggregatedStats");

                mb.appendString("Champion Kills: " + stats.get("totalChampionKills") + "\n")
                        .appendString("Minion Kills: " + stats.get("totalMinionKills") + "\n")
                        .appendString("Assists: " + stats.get("totalAssists") + "\n")
                        .appendString("Neutral Minion Kills: " + stats.get("totalNeutralMinionsKilled") + "\n")
                        .appendString("Turrets Killed: " + stats.get("totalTurretsKilled"));

                event.getChannel().sendMessage(mb.build());
            } catch (Exception e) {
                event.getChannel()
                        .sendMessage("Account not found or there was an error connecting to the LoL API\n"
                                + "Here is what I was able to get: \n");
                try {
                    event.getChannel().sendMessage(mb.build());
                } catch (Exception ex) {e.printStackTrace();}
                e.printStackTrace();
            }
        } catch (Exception e) {
            event.getChannel()
                    .sendMessage("Account not found or there was an error connecting to the LoL API\n"
                            + "Here is what I was able to get: \n");
            try {
                event.getChannel().sendMessage(mb.build());
            } catch (Exception ex) { e.printStackTrace();}
            e.printStackTrace();
        }
    }
}
