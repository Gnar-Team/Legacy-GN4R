package com.gmail.hexragon.gn4rBot.command.fun;

import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.managers.commands.annotations.Command;
import com.gmail.hexragon.gn4rBot.util.GnarMessage;
import net.dv8tion.jda.entities.Guild;
import net.dv8tion.jda.entities.User;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.gmail.hexragon.gn4rBot.managers.users.PermissionLevel.BOT_MASTER;
import static net.dv8tion.jda.utils.MiscUtil.getCreationTime;

@Command(
        aliases = {"gstats"},
        description = "Fancy server stats ya uuuuuurdd mi?",
        permissionRequired = BOT_MASTER,
        showInHelp = false
)
public class ServerGraphCommand extends CommandExecutor {
    @Override
    public void execute(GnarMessage message, String[] args) {
        message.getChannel().sendFile(drawPlot(getGuild(), message.getTime()), null);
    }

    public static File drawPlot(Guild guild, OffsetDateTime now)
    {
        long start = getCreationTime(guild.getId()).toEpochSecond();
        long end = now.toEpochSecond();
        int width = 1000;
        int height = 600;
        List<User> joins = new ArrayList<>(guild.getUsers());
        Collections.sort(joins, (User a, User b) -> guild.getJoinDateForUser(a).compareTo(guild.getJoinDateForUser(b)));
        BufferedImage bi = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB_PRE);
        Graphics2D g2d = bi.createGraphics();
        g2d.setComposite(AlphaComposite.SrcOver);
        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, width, height);
        g2d.setColor(Color.RED);
        int lastX = 5;
        int lastY = height;

        for(int i=0; i<joins.size(); i++)
        {
            int x = (int)(((guild.getJoinDateForUser(joins.get(i)).toEpochSecond() - start) * width) / (end-start));
            int y = height - ((i * height) / joins.size());
            g2d.drawLine(x, y, lastX, lastY);
            lastX=x;
            lastY=y;
        }
        g2d.setFont(g2d.getFont().deriveFont(24f));
        g2d.drawString("0 - "+joins.size()+" Users", 20, 26);
        g2d.drawString(getCreationTime(guild.getId()).format(DateTimeFormatter.RFC_1123_DATE_TIME), 20, 60);
        g2d.drawString(now.format(DateTimeFormatter.RFC_1123_DATE_TIME), 20, 90);
        g2d.drawString("Server: " + guild.getName(), 20, 120);
        g2d.drawString("Owner: " + guild.getOwner().getUsername(), 20, 150);
        File f = new File("plot.png");
        try {
            ImageIO.write(bi, "png", f);
        } catch (IOException ex) {
            System.out.println("[ERROR] An error occured drawing the plot.");
        }
        return f;
    }




}
