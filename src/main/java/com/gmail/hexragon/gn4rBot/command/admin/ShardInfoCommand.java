package com.gmail.hexragon.gn4rBot.command.admin;

import com.gmail.hexragon.gn4rBot.GnarBot;
import com.gmail.hexragon.gn4rBot.managers.GnarShard;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.managers.commands.annotations.Command;
import com.gmail.hexragon.gn4rBot.util.GnarMessage;

import static com.gmail.hexragon.gn4rBot.managers.users.PermissionLevel.BOT_MASTER;

@Command(
        aliases = {"shard"},
        description = "Get shard info",
        permissionRequired = BOT_MASTER,
        showInHelp = false
)
public class ShardInfoCommand extends CommandExecutor {
    @Override
    public void execute(GnarMessage message, String[] args) {
        String s = "";
        for(GnarShard shard : GnarBot.getShards()) {
            s += "Shard: " + shard.getShardID() + ": " + shard.getJDA().getGuilds().size() + "\n";
        }

        message.replyRaw(s);
    }
}