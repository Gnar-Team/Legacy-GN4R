package com.gmail.hexragon.gn4rBot.command.admin;

import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.managers.commands.annotations.Command;
import com.gmail.hexragon.gn4rBot.util.GnarMessage;

import static com.gmail.hexragon.gn4rBot.managers.users.PermissionLevel.BOT_MASTER;

@Command(
        aliases = {"update"},
        description = "Automatically update GN4R",
        permissionRequired = BOT_MASTER,
        showInHelp = false
)
public class UpdateShitCommand extends CommandExecutor {
    @Override
    public void execute(GnarMessage message, String[] args) {
        try {
            Process p = Runtime.getRuntime().exec("cd ../home/GN4R/");
            p.waitFor();

            p = Runtime.getRuntime().exec("git pull");
            p.wait();
            message.replyRaw("Attempting to pull updates...");

            p = Runtime.getRuntime().exec("rm -rf target");
            p.waitFor();

            p = Runtime.getRuntime().exec("mvn install package");
            p.waitFor();

            message.replyRaw("Compiling...");
            p = Runtime.getRuntime().exec("mv GN4R-BOT.jar ../../GNAR");
            p.waitFor();

            p = Runtime.getRuntime().exec("cd ../../GNAR");
            p.waitFor();

            p = Runtime.getRuntime().exec("java -jar GN4R-BOT.jar");
            p.waitFor();

            p=Runtime.getRuntime().exec("cd ../../../../../~");
            p.waitFor();
            message.reply("Restarting...");

            System.exit(0);
        } catch(Exception e) { message.reply("An error has occurred, sorry m8.");}
    }
}