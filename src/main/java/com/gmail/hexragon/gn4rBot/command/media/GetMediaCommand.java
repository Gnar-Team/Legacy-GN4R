package com.gmail.hexragon.gn4rBot.command.media;

import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandManager;
import com.gmail.hexragon.gn4rBot.managers.users.PermissionLevel;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

public class GetMediaCommand extends CommandExecutor
{
    public GetMediaCommand(CommandManager manager)
    {
        super(manager);
        setUsage("getMedia");
        setPermission(PermissionLevel.BOT_COMMANDER);
    }

    @Override
    public void execute(MessageReceivedEvent event, String[] args)
    {
        event.getChannel().sendMessage(args[1]);
    }
}
