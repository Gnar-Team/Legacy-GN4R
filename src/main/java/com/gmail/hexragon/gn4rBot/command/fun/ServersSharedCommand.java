package com.gmail.hexragon.gn4rBot.command.fun;

import com.gmail.hexragon.gn4rBot.GnarBot;
import com.gmail.hexragon.gn4rBot.managers.GnarShard;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.managers.commands.annotations.Command;
import com.gmail.hexragon.gn4rBot.util.GnarMessage;
import net.dv8tion.jda.entities.Guild;

@Command(
        aliases = {"shared", "serversshared"},
        description = "Tilts dumb people."
)
public class ServersSharedCommand extends CommandExecutor
{
    @Override
    public void execute(GnarMessage message, String[] args)
    {
        int total = 0;
        String servers = "";

        for(GnarShard shard : GnarBot.getShards()) {
            for(Guild g : shard.getJDA().getGuilds()) {
                if(g.getUsers().contains(message.getAuthor())) {
                    total++;
                    servers += "    **Server:** " + g.getName() + "\n";
                }
            }
        }

        message.replyRaw("**Total Servers:** " + total + "\n**Servers:** \n" + servers);
    }
}