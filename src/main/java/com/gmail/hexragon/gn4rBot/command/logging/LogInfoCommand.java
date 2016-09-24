package com.gmail.hexragon.gn4rBot.command.logging;

import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.managers.commands.annotations.Command;
import com.gmail.hexragon.gn4rBot.managers.commands.annotations.GuildDependent;
import com.gmail.hexragon.gn4rBot.managers.commands.annotations.ManagerDependent;
import com.gmail.hexragon.gn4rBot.util.GnarMessage;

import static com.gmail.hexragon.gn4rBot.managers.users.PermissionLevel.SERVER_OWNER;

@GuildDependent
@ManagerDependent
@Command(
        aliases = {"loginfo", "li"},
        description = "Server Logging information",
        permissionRequired = SERVER_OWNER,
        showInHelp = true
)
public class LogInfoCommand extends CommandExecutor {

    @Override
    public void execute(GnarMessage message, String[] args) {
        message.replyRaw(
                "**log-channel:** " + getGuildManager().getJSONObject().get("log-channel")
                + "\n**join-message-toggle:** " + getGuildManager().getJSONObject().get("join-message-toggle")
                + "\n**join-message:** " + getGuildManager().getJSONObject().get("join-message")
                + "\n**leave-message-toggle:** " + getGuildManager().getJSONObject().get("leave-message-toggle")
                + "\n**leave-message:** " + getGuildManager().getJSONObject().get("leave-message")



        );
    }
}