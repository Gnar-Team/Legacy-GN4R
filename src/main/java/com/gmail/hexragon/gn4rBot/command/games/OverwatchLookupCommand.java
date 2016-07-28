package com.gmail.hexragon.gn4rBot.command.games;

import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.StringJoiner;

import static com.gmail.hexragon.gn4rBot.util.Utils.readJsonFromUrl;

public class OverwatchLookupCommand extends CommandExecutor
{
	public OverwatchLookupCommand()
	{
		
		setDescription("Look up Overwatch information.");
		setUsage("(Battle Tag)");
	}
	
	@Override
	public void execute(MessageReceivedEvent event, String[] args)
	{
		if (args.length == 0)
		{
			event.getChannel().sendMessage(String.format("%s ➤ You need to provide a BattleTag.", event.getAuthor().getAsMention()));
			return;
		}
		
		if (!args[0].matches("[a-zA-Z1-9]+#\\d+"))
		{
			event.getChannel().sendMessage(String.format("%s ➤ You did not enter a valid BattleTag.", event.getAuthor().getAsMention()));
			return;
		}
		
		try
		{
			StringJoiner joiner = new StringJoiner("\n");
			
			JSONObject jso = readJsonFromUrl("https://owapi.net/api/v2/u/" + args[0].replaceAll("#", "-") + "/stats/general");
			
			if (jso == null)
			{
				event.getChannel().sendMessage(String.format("%s ➤ Unable to find Overwatch Player `%s`", event.getAuthor().getAsMention(), args[0]));
				return;
			}
			
			joiner.add("Battle Tag: **" + jso.getString("battletag") + "**");
			joiner.add("Region: **" + jso.getString("region").toUpperCase() + "**");
			
			JSONObject overallStats = new JSONObject(jso.getJSONObject("overall_stats").toString())
			{
				@Override
				public int getInt(String key) throws JSONException
				{
					try
					{
						return super.getInt(key);
					}
					catch (Exception e)
					{
						return -1;
					}
				}
			};
			joiner.add("\n**__General                                    __**");
			joiner.add("Level: **" + (overallStats.getInt("prestige")*100+overallStats.getInt("level")) + "**");
			joiner.add("Win Rate: **" + overallStats.getInt("win_rate") + "%**");
			joiner.add("Win Count: **" + overallStats.getInt("wins") + "**");
			joiner.add("Comp. Rank: **\uD83D\uDD30" + overallStats.getInt("comprank") + "**");
			
			JSONObject gameStats = new JSONObject(jso.getJSONObject("game_stats").toString())
			{
				@Override
				public double getDouble(String key) throws JSONException
				{
					try
					{
						return super.getDouble(key);
					}
					catch (Exception e)
					{
						return -1;
					}
				}
			};
			joiner.add("\n**__Overall                                    __**");
			joiner.add("Eliminations: **" + (int) gameStats.getDouble("eliminations") + "**");
			joiner.add("Medals: **" + (int) gameStats.getDouble("medals") + "**");
			joiner.add("Total Damage: **" + (int) gameStats.getDouble("damage_done") + "**");
			joiner.add("Cards: **" + (int) gameStats.getDouble("cards") + "**");

			event.getChannel().sendMessage(joiner.toString());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
