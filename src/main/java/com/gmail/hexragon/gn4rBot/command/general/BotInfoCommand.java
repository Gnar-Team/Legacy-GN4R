package com.gmail.hexragon.gn4rBot.command.general;

import com.gmail.hexragon.gn4rBot.GnarBot;
import com.gmail.hexragon.gn4rBot.managers.GnarShard;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.managers.commands.annotations.Command;
import com.gmail.hexragon.gn4rBot.managers.commands.annotations.ManagerDependent;
import com.gmail.hexragon.gn4rBot.util.GnarMessage;
import com.gmail.hexragon.gn4rBot.util.GnarQuotes;
import net.dv8tion.jda.JDA;
import net.dv8tion.jda.entities.Guild;
import net.dv8tion.jda.entities.User;

import java.util.StringJoiner;
import java.util.stream.Collectors;

@ManagerDependent
@Command(
        aliases = {"info", "botinfo"},
        description = "Show information about GN4R-BOT."
)
public class BotInfoCommand extends CommandExecutor
{
    public void execute(GnarMessage message, String[] args)
    {
        int channels;
        int textChannels = 0;
        int voiceChannels = 0;
        int servers = 0;
        int users = 0;
        int offline = 0;
        int online = 0;
        int inactive = 0;
        
        for (GnarShard shard : GnarBot.getShards())
        {
            JDA jda = shard.getJDA();
            
            servers += jda.getGuilds().size();
            for (Guild g : jda.getGuilds())
            {
                for (User u : g.getUsers())
                {
                    switch (u.getOnlineStatus())
                    {
                        case ONLINE:
                            online++;
                            break;
                        case OFFLINE:
                            offline++;
                            break;
                        case AWAY:
                            inactive++;
                            break;
                    }
                }
                users += g.getUsers().size();
                textChannels += g.getTextChannels().size();
                voiceChannels += g.getVoiceChannels().size();
            }
        }
        channels = textChannels + voiceChannels;
        
        
        int commandSize = getCommandManager().getUniqueRegistry().values().parallelStream()
                .filter(CommandExecutor::isShownInHelp)
                .collect(Collectors.toList())
                .size();
        
        int requests = GnarBot.getGuildManagers().stream()
                .mapToInt(guild -> guild.getCommandManager().getRequests())
                .sum();
        
        
        StringJoiner joiner = new StringJoiner("\n");
        
        joiner.add("\u258C Requests ____ " + requests);
        joiner.add("\u258C Servers _____ " + servers);
        joiner.add("\u258C Shards ______ " + GnarBot.getShards().size());
        joiner.add("\u258C");
        joiner.add("\u258C Channels ____ " + channels);
        joiner.add("\u258C  - Text _____ " + textChannels);
        joiner.add("\u258C  - Voice ____ " + voiceChannels);
        joiner.add("\u258C");
        joiner.add("\u258C Users _______ " + users);
        joiner.add("\u258C  - Online ___ " + online);
        joiner.add("\u258C  - Offline __ " + offline);
        joiner.add("\u258C  - Inactive _ " + inactive);
        joiner.add("\u258C");
        joiner.add("\u258C Creator _____ Avalon & Maeyrl");
        joiner.add("\u258C Website _____ gnarbot.xyz");
        joiner.add("\u258C Commands ____ " + commandSize);
        joiner.add("\u258C Library _____ JDA");
        joiner.add("\u258C Uptime ______ " + GnarBot.getUptimeStamp(false) + ".");
        
        message.reply("**" + GnarQuotes.getRandomQuote() + "** Here is all of my information!");
        
        message.getChannel().sendMessage("```xl\n" + joiner.toString() + "```\n");
    }
}
