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
import com.gmail.hexragon.gn4rBot.util.FileManager;
import com.gmail.hexragon.gn4rBot.util.NullableJSONObject;
import net.dv8tion.jda.entities.Guild;
import net.dv8tion.jda.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import org.json.JSONObject;

import java.io.File;

public class GuildManager extends net.dv8tion.jda.managers.GuildManager implements GnarManager
{
    private final String accessID;
    private final UserManager userManager;
    private final CommandManager commandManager;
    private final ServerManager manager;
    
    private final FileManager fileManager;
    private final JSONObject jsonObject;
    
    public GuildManager(String accessID, ServerManager manager, Guild guild)
    {
        super(guild);
    
        this.accessID = accessID;
        this.manager = manager;
    
        String basePath = String.format("_DATA/servers/%s.json", accessID);
        fileManager = new FileManager(basePath);
        fileManager.createIfNotFound();
    
        String content = fileManager.readText();
        
        if (content.length() == 0) jsonObject = new NullableJSONObject();
        else jsonObject = new NullableJSONObject(content);
        
        saveFile();
    
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
        commandManager.registerCommand(ASCIICommand.class);
        commandManager.registerCommand(LeetifyCommand.class);
        commandManager.registerCommand(PoopCommand.class);
        commandManager.registerCommand(MarvelComics.class);
        commandManager.registerCommand(DialogCommand.class);
        commandManager.registerCommand(ProgressionCommand.class);
        
        commandManager.registerCommand(GameLookupCommand.class);
        commandManager.registerCommand(LeagueLookupCommand.class);
        commandManager.registerCommand(OverwatchLookupCommand.class);
        
        commandManager.registerCommand(Rule34Command.class);
        commandManager.registerCommand(DiscordBotsUserInfoCommand.class);
        commandManager.registerCommand(UpdateShitCommand.class);
        commandManager.registerCommand(ListBotsCommand.class);
        commandManager.registerCommand(ListServersCommand.class);
        commandManager.registerCommand(ServerGraphCommand.class);

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
    
    @Override
    public void handleUserJoin(GuildMemberJoinEvent event)
    {
        
    }
    
    @Override
    public void handleUserLeave(GuildMemberLeaveEvent event)
    {
        
    }
    
    @Override
	public File getFile()
	{
		return fileManager.getFile();
	}
    
    @Override
    public void saveFile()
    {
        fileManager.writeText(jsonObject.toString(4));
    }
}
