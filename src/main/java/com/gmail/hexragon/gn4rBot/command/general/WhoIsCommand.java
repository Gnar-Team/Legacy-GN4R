package com.gmail.hexragon.gn4rBot.command.general;

import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.managers.commands.annotations.Command;
import com.gmail.hexragon.gn4rBot.managers.commands.annotations.GuildDependent;
import com.gmail.hexragon.gn4rBot.util.GnarMessage;
import net.dv8tion.jda.entities.Game;
import net.dv8tion.jda.entities.User;

import java.util.StringJoiner;

@GuildDependent
@Command(
        aliases = {"whois", "infoof", "infoon"},
        usage = "(@user)",
        description = "Get information on a user."
)
public class WhoIsCommand extends CommandExecutor
{
    
    @Override
    public void execute(GnarMessage message, String[] args)
    {
        
        if (args.length == 0)
        {
            message.reply("You did not mention a valid user.");
            return;
        }
        
        User user = message.getMentionedUsers().get(0);
        
        if (user == null)
        {
            message.reply("You did not mention a valid user.");
            return;
        }
        
        StringBuilder mainBuilder = new StringBuilder();
        
        
        String nickname = getGuildManager().getGuild().getNicknameForUser(user);
        Game game = user.getCurrentGame();
        String avatarID = user.getAvatarId();
        String avatarURL = user.getAvatarUrl();
        
        StringJoiner metaBuilder = new StringJoiner("\n");
        metaBuilder.add("\u258C ID _________ " + user.getId());
        metaBuilder.add("\u258C Name _______ " + user.getUsername());
        metaBuilder.add("\u258C Nickname ___ " + (nickname != null ? nickname : "None"));
        metaBuilder.add("\u258C Game _______ " + (game != null ? game.getName() : "None"));
        metaBuilder.add("\u258C Avatar _____ " + (avatarID != null ? avatarID : "Error"));
        metaBuilder.add("\u258C Disc. ______ " + user.getDiscriminator());
        metaBuilder.add("\u258C Bot ________ " + String.valueOf(user.isBot()).toUpperCase());
        metaBuilder.add("\u258C Gn4r Perm __ " + getUserManager().getGnarUser(user).getPermission().toString().replaceAll("_", " "));
        metaBuilder.add("\u258C \n");
        
        mainBuilder.append(metaBuilder.toString());
        
        mainBuilder.append("\u258C Roles ______ ").append(getGuildManager().getGuild().getRolesForUser(user).size()).append('\n');
        getGuildManager().getGuild().getRolesForUser(user).stream()
                .filter(role -> !mainBuilder.toString().contains(role.getId()))
                .forEach(role -> mainBuilder.append("\u258C  - ").append(role.getName()).append('\n'));
        
        message.replyRaw("```xl\n" + mainBuilder.toString().replaceAll("null", "None") + "```");
    }
}
