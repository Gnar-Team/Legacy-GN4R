package com.gmail.hexragon.gn4rBot.command.fun;

import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandManager;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

public class GameLookupCommand extends CommandExecutor
{
    public GameLookupCommand(CommandManager manager)
    {
        super(manager);
        showInHelp(false);
    }

    @Override
    public void execute(MessageReceivedEvent event, String[] args)
    {
        try {
            HttpResponse<JsonNode> response = Unirest.get("https://videogamesrating.p.mashape.com/get.php?count=5&game=Call+of+Duty+Advanced+Warfare")
                    .header("X-Mashape-Key", "SrKRceX2hQmshTs1Ngl6C8MdLPCkp1DGpPPjsnSfF2IDI8aMqL")
                    .header("Accept", "application/json")
                    .asJson();
            System.out.println(response.getBody().toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
