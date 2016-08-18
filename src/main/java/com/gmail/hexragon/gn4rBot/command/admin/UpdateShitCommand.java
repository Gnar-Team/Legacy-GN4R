package com.gmail.hexragon.gn4rBot.command.admin;

import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.managers.commands.annotations.Command;
import com.gmail.hexragon.gn4rBot.util.GnarMessage;
import net.dv8tion.jda.entities.Message;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

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
            Runtime rt = Runtime.getRuntime();
            Message m = message.getChannel().sendMessage("*Now updating...*\n\nRunning `git clone`... ");

            try {
                Process rm = rt.exec("rm -rf update");
                rm.waitFor(5, TimeUnit.SECONDS);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            Process gitClone = rt.exec("git pull");
            BufferedReader reader = new BufferedReader(new InputStreamReader(gitClone.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null)
            {
                System.out.println(line);
            }//reached end of file when process exits
            if (gitClone.waitFor(120, TimeUnit.SECONDS) == false) {
                m.updateMessage("Git Clone: [:anger: timed out]\n\n");
                throw new RuntimeException("Operation timed out: git clone");
            } else if (gitClone.exitValue() != 0) {
                m.updateMessage("Git Clone: [:anger: returned code " + gitClone.exitValue() + "]\n\n");
                throw new RuntimeException("Bad response code");
            }

            m.updateMessage(message.getRawContent() + "👌🏽\n\nRunning `mvn package install`... ");
            File updateDir = new File("/home/GNAR/pom.xml");
            Process mvnBuild = rt.exec("/usr/bin/mvn -f " + updateDir.getAbsolutePath() + " package install shade:shade");
            reader = new BufferedReader(new InputStreamReader(mvnBuild.getInputStream()));

            while ((line = reader.readLine()) != null)
            {
                System.out.println(line);
            }//reached end of file when process exits
            mvnBuild.waitFor();

            Process moveFolder = rt.exec("mv /home/GNAR/target/original-GN4R-BOT.jar /home/GNAR/");
            reader = new BufferedReader(new InputStreamReader(moveFolder.getInputStream()));


            while ((line = reader.readLine()) != null)
            {
                System.out.println(line);
            }//reached end of file when process exits
            if (moveFolder.waitFor(300, TimeUnit.SECONDS) == false) {
                m.updateMessage("Move File: [:anger: timed out]\n\n");
                throw new RuntimeException("Operation timed out: mvn package shade:shade");
            } else if (moveFolder.exitValue() != 0) {
                m.updateMessage("Move File: [:anger: returned code " + moveFolder.exitValue() + "]\n\n");
                throw new RuntimeException("Bad response code");
            }
            moveFolder.waitFor();

            Process runNew = rt.exec("java -jar /home/GNAR/original-GN4R-BOT.jar");
            reader = new BufferedReader(new InputStreamReader(moveFolder.getInputStream()));


            while ((line = reader.readLine()) != null)
            {
                System.out.println(line);
            }//reached end of file when process exits
            runNew.waitFor();
            reader.close();
            if (runNew.waitFor(300, TimeUnit.SECONDS) == false) {
                m.updateMessage("Rerunning: [:anger: timed out]\n\n");
                throw new RuntimeException("Operation timed out: mvn package shade:shade");
            } else if (runNew.exitValue() != 0) {
                m.updateMessage("Rerunning: [:anger: returned code " + runNew.exitValue() + "]\n\n");
                throw new RuntimeException("Bad response code");
            }

            /*
            //Read and execute sh script
            InputStream is = this.getClass().getClassLoader().getResourceAsStream("update.sh");
            BufferedReader in = new BufferedReader(new InputStreamReader(is));
            String inputLine;
            String source = "";
            while ((inputLine = in.readLine()) != null) {
                source = source + inputLine + "\n";
            }
            in.close();
            rt.exec(source);*/

            //Shutdown for update
            m.updateMessage("👌🏽\n\nNow restarting...");

            System.exit(0);
        } catch (InterruptedException | IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}