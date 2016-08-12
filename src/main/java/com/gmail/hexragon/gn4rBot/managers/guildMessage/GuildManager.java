package com.gmail.hexragon.gn4rBot.managers.guildMessage;

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
import com.gmail.hexragon.gn4rBot.managers.commands.CommandManager;
import com.gmail.hexragon.gn4rBot.managers.servers.GnarManager;
import com.gmail.hexragon.gn4rBot.managers.servers.ServerManager;
import com.gmail.hexragon.gn4rBot.managers.users.UserManager;
import net.dv8tion.jda.entities.Guild;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

public class GuildManager extends net.dv8tion.jda.managers.GuildManager implements GnarManager
{
    private final String accessID;
    private final UserManager userManager;
    private final CommandManager commandManager;
    private final ServerManager manager;
    
    private String basePath;
    //private final File baseFile;
    
    public GuildManager(String accessID, ServerManager manager, Guild guild)
    {
        super(guild);
        
        this.accessID = accessID;
        this.basePath = String.format("_DATA/servers/%s/", accessID);
        this.manager = manager;

//		baseFile = new File(basePath);
//		if (!baseFile.exists()) //noinspection ResultOfMethodCallIgnored
//			baseFile.mkdirs();
        
        this.userManager = new UserManager(this);
        this.commandManager = new GuildCommandManager(this);
        
        defaultSetup();
    }
    
    private void defaultSetup()
    {
        commandManager.registerCommand(HelpCommand.class);
        commandManager.registerCommand(BotInfoCommand.class);
        commandManager.registerCommand(WhoIsCommand.class);
        commandManager.registerCommand(InviteBotCommand.class);
        commandManager.registerCommand(UptimeCommand.class);
        commandManager.registerCommand(MathCommand.class);
        commandManager.registerCommand(PingCommand.class);
        commandManager.registerCommand(RemindMeCommand.class);
        
        commandManager.registerCommand(RollCommand.class);
        commandManager.registerCommand(CoinFlipCommand.class);
        commandManager.registerCommand(EightBallCommand.class);
        
        commandManager.registerCommand(GoogleCommand.class);
        commandManager.registerCommand(YoutubeCommand.class);
        
        commandManager.registerCommand(BanCommand.class);
        commandManager.registerCommand(UnbanCommand.class);
        commandManager.registerCommand(MuteCommand.class);
        commandManager.registerCommand(UnmuteCommand.class);
        commandManager.registerCommand(DeleteMessagesCommand.class);
        
        commandManager.registerCommand(CleverbotCommand.class);
        commandManager.registerCommand(PandorabotCommand.class);
        commandManager.registerCommand(TextToSpeechCommand.class);
        
        commandManager.registerCommand(xkcdCommand.class);
        commandManager.registerCommand(ExplosmCommand.class);
        commandManager.registerCommand(ExplosmRCGCommand.class);
        commandManager.registerCommand(GarfieldCommand.class);
        commandManager.registerCommand(GetMediaCommand.class);
        commandManager.registerCommand(ListMediaCommand.class);
        commandManager.registerCommand(CatsCommand.class);
        
        
        commandManager.registerCommand(GoogleyEyesCommand.class);
        commandManager.registerCommand(DiscordGoldCommand.class);
        commandManager.registerCommand(GoodShitCommand.class);
        commandManager.registerCommand(YodaTalkCommand.class);
        commandManager.registerCommand(ZalgoCommand.class);
        commandManager.registerCommand(ASCIICommand.class);
        commandManager.registerCommand(LeetifyCommand.class);
        commandManager.registerCommand(PoopCommand.class);
        
        
        commandManager.registerCommand(GameLookupCommand.class);
        commandManager.registerCommand(LeagueLookupCommand.class);
        commandManager.registerCommand(OverwatchLookupCommand.class);
        
        commandManager.registerCommand(Rule34Command.class);
        commandManager.registerCommand(DiscordBotsUserInfoCommand.class);


//		commandManager.builder("kotlin_test").executor(KOTLIN_KotlinBase.class);
//		commandManager.builder("kotlin_xkcd").executor(KOTLIN_xkcdCommand.class);
//		commandManager.builder("test").executor(TestCommand.class);


//		commandManager.builder("joinchannel").executor(JoinChannelCommand.class);
//		commandManager.builder("queue").executor(QueueCommand.class);
//		commandManager.builder("volume").executor(VolumeCommand.class);
//		commandManager.builder("play", "resume").executor(PlayCommand.class);
//		commandManager.builder("leavechannel").executor(LeaveChannelCommand.class);
//		commandManager.builder("nowplaying").executor(NowPlayingCommand.class);
//		commandManager.builder("repeat").executor(RepeatCommand.class);
//		commandManager.builder("skip").executor(SkipCommand.class);
//		commandManager.builder("stop").executor(StopCommand.class);
//		commandManager.builder("restart").executor(RestartCommand.class);
//		commandManager.builder("resetmusic").executor(ResetCommand.class);
        
        //commandManager.registerCommand(ReassignTokenCommand.class);
        commandManager.registerCommand(JavascriptCommand.class);
        //commandManager.registerCommand(ReassignPermissionCommand.class);
        commandManager.registerCommand(DiagnosticsCommand.class);
        commandManager.registerCommand(ArgsTestCommand.class);
        commandManager.registerCommand(ThrowError.class);
        commandManager.registerCommand(ProcessHTML.class);
    }
    
    @Override
    public CommandManager getCommandManager()
    {
        return commandManager;
    }
    
    
    @Override
    public UserManager getUserManager()
    {
        return userManager;
    }
    
    
    @Override
    public String getAccessID()
    {
        return accessID;
    }
    
    
    @Override
    public ServerManager getServerManager()
    {
        return manager;
    }
    
    
    @Override
    public void handleMessageEvent(MessageReceivedEvent event)
    {
        getCommandManager().callCommand(event);
    }

//
//	public File getBaseFile()
//	{
//		return baseFile;
//	}
    
    
    @Override
    public String getBasePath()
    {
        return basePath;
    }
    
}
