package com.gmail.hexragon.gn4rBot.managers.servers;

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
import com.gmail.hexragon.gn4rBot.managers.users.UserManager;
import net.dv8tion.jda.entities.Guild;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import net.dv8tion.jda.managers.GuildManager;

public class GnarGuild extends GuildManager
{
	private final String accessID;
	private final UserManager userManager;
	private final CommandManager commandManager;
	private final ServerManagers manager;

	private final String basePath;
	//private final File baseFile;
	
	public GnarGuild(String accessID, ServerManagers manager, Guild guild)
	{
		super(guild);

		this.accessID = accessID;
		this.basePath = String.format("_DATA/servers/%s/", accessID);
		this.manager = manager;

//		baseFile = new File(basePath);
//		if (!baseFile.exists()) //noinspection ResultOfMethodCallIgnored
//			baseFile.mkdirs();

		this.userManager = new UserManager(this);
		this.commandManager = new CommandManager(this);
	}

	public void defaultSetup()
	{
		commandManager.registerCommand(HelpCommand.class);
		commandManager.registerCommand(BotInfoCommand.class);
		commandManager.registerCommand(WhoIsCommand.class);
		commandManager.registerCommand(InviteBotCommand.class);
		commandManager.registerCommand(UptimeCommand.class);
		commandManager.registerCommand(MathCommand.class);
		commandManager.registerCommand(PingCommand.class);
		
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
		
		
		commandManager.registerCommand(GameLookupCommand.class);
		commandManager.registerCommand(LeagueLookupCommand.class);
		commandManager.registerCommand(OverwatchLookupCommand.class);
		

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
		
		commandManager.registerCommand(ReassignTokenCommand.class);
		commandManager.registerCommand(JavascriptCommand.class);
		commandManager.registerCommand(ReassignPermissionCommand.class);
		commandManager.registerCommand(DiagnosticsCommand.class);
		commandManager.registerCommand(ArgsTestCommand.class);
	}
	
	public CommandManager getCommandManager()
	{
		return commandManager;
	}

	
	public UserManager getUserManager()
	{
		return userManager;
	}

	
	public String getAccessID()
	{
		return accessID;
	}

	
	public ServerManagers getServerManager()
	{
		return manager;
	}

	
	public void handleMessageEvent(MessageReceivedEvent event)
	{
		getCommandManager().callCommand(event);
	}

//
//	public File getBaseFile()
//	{
//		return baseFile;
//	}

	
	public String getBasePath()
	{
		return basePath;
	}

}
