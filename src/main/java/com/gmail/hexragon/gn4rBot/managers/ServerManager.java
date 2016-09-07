package com.gmail.hexragon.gn4rBot.managers;

import com.gmail.hexragon.gn4rBot.command.admin.*;
import com.gmail.hexragon.gn4rBot.command.ai.CleverbotCommand;
import com.gmail.hexragon.gn4rBot.command.ai.PandorabotCommand;
import com.gmail.hexragon.gn4rBot.command.fun.*;
import com.gmail.hexragon.gn4rBot.command.games.GameLookupCommand;
import com.gmail.hexragon.gn4rBot.command.games.LeagueLookupCommand;
import com.gmail.hexragon.gn4rBot.command.games.OverwatchLookupCommand;
import com.gmail.hexragon.gn4rBot.command.general.*;
import com.gmail.hexragon.gn4rBot.command.media.*;
import com.gmail.hexragon.gn4rBot.command.mod.*;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.managers.commands.annotations.Command;
import com.gmail.hexragon.gn4rBot.managers.commands.annotations.GuildDependent;
import com.gmail.hexragon.gn4rBot.managers.commands.annotations.ManagerDependent;
import com.gmail.hexragon.gn4rBot.util.DiscordBotsInfo;
import com.gmail.hexragon.gn4rBot.util.MediaCache;
import net.dv8tion.jda.JDA;
import net.dv8tion.jda.entities.Guild;
import net.dv8tion.jda.events.ShutdownEvent;
import net.dv8tion.jda.events.guild.GuildJoinEvent;
import net.dv8tion.jda.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import net.dv8tion.jda.hooks.ListenerAdapter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * Handles multiinstances of Guilds.
 */
public class ServerManager extends CommandRegistry
{
    private final JDA jda;
    
    private final MediaCache mediaCache = new MediaCache();
    
    private final Map<String, GuildManager> serverMap = new HashMap<>();
    
    private final GuildManager privateGuild;
    
    private final List<Class<? extends CommandExecutor>> managerCMDRegistry = new ArrayList<>();
    private final List<Class<? extends CommandExecutor>> guildCMDRegistry = new ArrayList<>();
    
    public ServerManager(JDA jda)
    {
        this.jda = jda;
    
        defaultSetup();
        
        this.privateGuild = new GuildManager("DM", this, null, true);
        
        this.jda.addEventListener(new ListenerAdapter()
        {
            @Override
            public void onMessageReceived(MessageReceivedEvent event)
            {
                if (event.getAuthor().isBot())
                {
                    return;
                }
                if (event.isPrivate())
                {
                    privateGuild.handleMessageEvent(event);
                    return;
                }
    
                if (!serverMap.containsKey(event.getGuild().getId())) addServer(event.getGuild());
                GuildManager server = serverMap.get(event.getGuild().getId());
                server.handleMessageEvent(event);
            }
    
            @Override
            public void onGuildMemberJoin(GuildMemberJoinEvent event)
            {
                if (!serverMap.containsKey(event.getGuild().getId())) addServer(event.getGuild());
                GuildManager server = serverMap.get(event.getGuild().getId());
                server.handleUserJoin(event);
            }

            @Override
            public void onGuildMemberLeave(GuildMemberLeaveEvent event)
            {
                if (!serverMap.containsKey(event.getGuild().getId())) addServer(event.getGuild());
                GuildManager server = serverMap.get(event.getGuild().getId());
                server.handleUserLeave(event);
            }

            @Override
            public void onShutdown(ShutdownEvent event)
            {
                getGuildManagers().forEach(GuildManager::saveFile);
                getMediaCache().store();
            }

            @Override
            public void onGuildJoin(GuildJoinEvent event)
            {
                DiscordBotsInfo.updateServerCount(jda);
            }

            @Override
            public void onGuildLeave(GuildLeaveEvent event)
            {
                DiscordBotsInfo.updateServerCount(jda);
            }
        });
       
    }
    
    private void addServer(Guild server)
    {
        serverMap.put(server.getId(), new GuildManager(server.getId(), this, server, false));
    }
    
    public GuildManager getGuildManager(String accessID)
    {
        return serverMap.get(accessID);
    }
    
    void defaultSetup()
    {
        registerCommand(HelpCommand.class);
        registerCommand(BotInfoCommand.class);
        registerCommand(WhoIsCommand.class);
        registerCommand(InviteBotCommand.class);
        registerCommand(UptimeCommand.class);
        registerCommand(MathCommand.class);
        registerCommand(PingCommand.class);
        registerCommand(RemindMeCommand.class);
    
        registerCommand(RollCommand.class);
        registerCommand(CoinFlipCommand.class);
        registerCommand(EightBallCommand.class);
    
        registerCommand(GoogleCommand.class);
        registerCommand(YoutubeCommand.class);
    
        registerCommand(BanCommand.class);
        registerCommand(UnbanCommand.class);
        registerCommand(MuteCommand.class);
        registerCommand(UnmuteCommand.class);
        registerCommand(DeleteMessagesCommand.class);
    
        registerCommand(CleverbotCommand.class);
        registerCommand(PandorabotCommand.class);
        registerCommand(TextToSpeechCommand.class);
    
        registerCommand(xkcdCommand.class);
        registerCommand(ExplosmCommand.class);
        registerCommand(ExplosmRCGCommand.class);
        registerCommand(GarfieldCommand.class);
        registerCommand(GetMediaCommand.class);
        registerCommand(ListMediaCommand.class);
        registerCommand(CatsCommand.class);
        
        registerCommand(GoogleyEyesCommand.class);
        registerCommand(DiscordGoldCommand.class);
        registerCommand(GoodShitCommand.class);
        registerCommand(YodaTalkCommand.class);
        registerCommand(ASCIICommand.class);
        registerCommand(LeetifyCommand.class);
        registerCommand(PoopCommand.class);
        registerCommand(MarvelComics.class);
        registerCommand(DialogCommand.class);
        registerCommand(ProgressionCommand.class);
    
        registerCommand(GameLookupCommand.class);
        registerCommand(LeagueLookupCommand.class);
        registerCommand(OverwatchLookupCommand.class);
    
        registerCommand(Rule34Command.class);
        registerCommand(DiscordBotsUserInfoCommand.class);
        registerCommand(UpdateShitCommand.class);
        registerCommand(ListBotsCommand.class);
        registerCommand(ListServersCommand.class);
        registerCommand(ServerGraphCommand.class);
        registerCommand(GraphCommand.class);
    
        registerCommand(JavascriptCommand.class);
        registerCommand(DiagnosticsCommand.class);
        registerCommand(ArgsTestCommand.class);
        registerCommand(ThrowError.class);
    }
    
    @Override
    public void registerCommand(Class<? extends CommandExecutor> cls) throws IllegalStateException
    {
        if (!cls.isAnnotationPresent(Command.class))
        {
            throw new IllegalStateException(cls + ": @Command annotation not found.");
        }

        if (cls.isAnnotationPresent(ManagerDependent.class))
        {
            managerCMDRegistry.add(cls);
        }
        else if (!cls.isAnnotationPresent(GuildDependent.class))
        {
            try
            {
                CommandExecutor cmd = cls.newInstance();
        
                Command meta = cls.getAnnotation(Command.class);
        
                cmd.setDescription(meta.description());
                cmd.setPermission(meta.permissionRequired());
                cmd.showInHelp(meta.showInHelp());
                cmd.setUsage(meta.usage());
        
                for (Field field : cmd.getClass().getSuperclass().getDeclaredFields())
                {
                    if (field.getType().isAssignableFrom(this.getClass()))
                    {
                        field.setAccessible(true);
                        field.set(cmd, this);
                    }
                }
                
                for (String s : meta.aliases())
                {
                    getCommandRegistry().put(s, cmd);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        
        else
        {
            guildCMDRegistry.add(cls);
        }
    }
    
    public List<GuildManager> getGuildManagers()
    {
        return new ArrayList<>(serverMap.values());
    }
    
    public MediaCache getMediaCache()
    {
        return mediaCache;
    }
    
    public List<Class<? extends CommandExecutor>> getManagerCommandRegistry()
    {
        return managerCMDRegistry;
    }
    
    public List<Class<? extends CommandExecutor>> getGuildCommandRegistry()
    {
        return guildCMDRegistry;
    }
}
