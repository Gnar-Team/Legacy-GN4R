package com.gmail.hexragon.gn4rBot.command.fun;

import com.gmail.hexragon.gn4rBot.GnarBot;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.managers.commands.annotations.Command;
import com.gmail.hexragon.gn4rBot.util.GnarMessage;
import com.gmail.hexragon.gn4rBot.util.Utils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

@Command(
        aliases = {"marvel"},
        usage = "(hero/villain name)",
        description = "Look up info on a Marvel character."
)
public class MarvelComics extends CommandExecutor
{
    @Override
    public void execute(GnarMessage message, String[] args)
    {
        if (args.length == 0)
        {
            message.reply("Please provide a name.");
            return;
        }
        
        try
        {
            long timeStamp = System.currentTimeMillis();
            String ts = String.valueOf(timeStamp);
            String apiKeyPu = GnarBot.getAuthTokens().get("marvelPuToken");
            String apiKeyPr = GnarBot.getAuthTokens().get("marvelPrToken");
            String preHash = timeStamp + apiKeyPr + apiKeyPu;
            String hash = DigestUtils.md5Hex(preHash);
            
            String s = StringUtils.join(args, "+");
            
            JSONObject jso = Utils.readJsonFromUrl("http://gateway.marvel.com:80/v1/public/characters?apikey=" + apiKeyPu + "&hash=" + hash + "&name=" + s + "&ts=" + ts);
            JSONObject je = jso.getJSONObject("data");
            JSONArray j2 = je.getJSONArray("results");
            JSONObject j = j2.getJSONObject(0);


//            System.out.println(jso);
//
//            System.out.println(je);
//
//            System.out.println(j2);
//
//            System.out.println(j);
            
            JSONObject thumb = (JSONObject) j.get("thumbnail");
            
            
            //message.reply("http://gateway.marvel.com:80/v1/public/characters?apikey=" + apiKeyPu + "&hash=" + hash + "&name="+s+"&ts="+ts);
            message.reply("Thumbnail: " + thumb.get("path") + "." + thumb.get("extension"));
            
            //System.out.println(thumb);


            /*String ne = "http://gateway.marvel.com/v1/public/comics/394?apikey=";
            String ol = "http://gateway.marvel.com:80/v1/public/characters?apikey=";
            URL url = new URL(ne + apiKeyPu + "&hash=" + hash+ "&ts=" + ts);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            message.reply("URL: " + ne + apiKeyPu + "&hash=" + hash+ "&ts=" + ts);
            message.reply(con.getResponseCode()+"");*/
        }
        catch (Exception e)
        {
            message.reply("Character not found.");
        }
    }
}
