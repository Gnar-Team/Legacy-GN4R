package com.gmail.hexragon.gn4rBot.command.fun;

import com.gmail.hexragon.gn4rBot.managers.commands.Command;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.util.DiscordBotsUserInfo;
import com.gmail.hexragon.gn4rBot.util.GnarMessage;

@Command(
        aliases = {"dui", "bi", "discordbots", "discordpw"},
        usage = "(string)",
        description = "Get Discord user information"
)
public class DiscordBotsUserInfoCommand extends CommandExecutor {

    @Override
    public void execute(GnarMessage message, String[] args) {
        if(args.length > 0) {
            if (message.getMentionedUsers().size() == 0) {
                String data = DiscordBotsUserInfo.getUserInfo(args[0]);
                message.reply(data);
            } else {
                String data = DiscordBotsUserInfo.getUserInfo(message.getMentionedUsers().get(0).getId());
                message.reply(data);

            }
        } else {
            message.reply("You must supply a bot ID or mention.");
        }
    }
}
