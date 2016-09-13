package com.gmail.hexragon.gn4rBot.command.admin;

import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.managers.commands.annotations.Command;
import com.gmail.hexragon.gn4rBot.util.GnarMessage;
import net.dv8tion.jda.entities.Emote;
import net.dv8tion.jda.entities.Guild;

import java.util.ArrayList;

import static com.gmail.hexragon.gn4rBot.managers.users.PermissionLevel.BOT_MASTER;

@Command(
        aliases = {"emoji"},
        description = "Automatically update GN4R",
        permissionRequired = BOT_MASTER,
        showInHelp = false
)
public class UpdateShitCommand extends CommandExecutor {
    @Override
    public void execute(GnarMessage message, String[] args) {
        ArrayList<String> ar = new ArrayList<>();

        for(Guild g : message.getJDA().getGuilds()) {
            for(Emote e : g.getEmotes()) {
                ar.add(e.getAsEmote());
            }
        }

        String mess = "";

        for(String s : ar) {
            if(mess.length()+s.length() >= 2000) {
                message.replyRaw(mess);
                mess = "";
            } else {
                mess += s + " ";
            }
        }
        message.replyRaw(mess);
    }
}