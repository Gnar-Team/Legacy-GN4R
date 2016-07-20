package com.gmail.hexragon.gn4rBot.managers.servers;

import com.gmail.hexragon.gn4rBot.command.admin.JavascriptCommand;
import com.gmail.hexragon.gn4rBot.command.admin.ReassignPermissionCommand;
import com.gmail.hexragon.gn4rBot.command.admin.ReassignTokenCommand;
import com.gmail.hexragon.gn4rBot.command.ai.CleverbotCommand;
import com.gmail.hexragon.gn4rBot.command.ai.PandorabotCommand;
import com.gmail.hexragon.gn4rBot.command.fun.*;
import com.gmail.hexragon.gn4rBot.command.general.BotInfoCommand;
import com.gmail.hexragon.gn4rBot.command.general.HelpCommand;
import com.gmail.hexragon.gn4rBot.command.general.InviteBotCommand;
import com.gmail.hexragon.gn4rBot.command.general.WhoIsCommand;
import com.gmail.hexragon.gn4rBot.command.media.CatsCommand;
import com.gmail.hexragon.gn4rBot.command.media.GetMediaCommand;
import com.gmail.hexragon.gn4rBot.command.media.ListMediaCommand;
import com.gmail.hexragon.gn4rBot.command.mod.BanCommand;
import com.gmail.hexragon.gn4rBot.command.mod.MuteCommand;
import com.gmail.hexragon.gn4rBot.command.mod.UnmuteCommand;
import com.gmail.hexragon.gn4rBot.command.music.*;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandManager;
import com.gmail.hexragon.gn4rBot.managers.users.UserManager;
import net.dv8tion.jda.entities.Guild;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

import java.io.File;

public class GnarGuild extends net.dv8tion.jda.managers.GuildManager
{
	private final String accessID;
	private final UserManager userManager;
	private final CommandManager commandManager;
	private final GuildManager manager;

	private final String basePath;
	private final File baseFile;
	
	public GnarGuild(String accessID, GuildManager manager, Guild guild)
	{
		super(guild);

		this.accessID = accessID;
		this.basePath = String.format("_DATA/servers/%s/", accessID);
		this.manager = manager;

		baseFile = new File(basePath);
		if (!baseFile.exists()) //noinspection ResultOfMethodCallIgnored
			baseFile.mkdirs();

		this.userManager = new UserManager(this);
		this.commandManager = new CommandManager(this);
		//NOTE: you need to create base folder before server
	}

	public void defaultSetup()
	{
		commandManager.builder("help", "guide").executor(HelpCommand.class);
		commandManager.builder("info", "botinfo").executor(BotInfoCommand.class);
		commandManager.builder("whois", "infoon", "infoof").executor(WhoIsCommand.class);
		commandManager.builder("invite", "invitebot").executor(InviteBotCommand.class);

		commandManager.builder("ban").executor(BanCommand.class);
		commandManager.builder("mute").executor(MuteCommand.class);
		commandManager.builder("unmute").executor(UnmuteCommand.class);

		commandManager.builder("cbot", "cleverbot").executor(CleverbotCommand.class);
		commandManager.builder("pbot", "pandorabot").executor(PandorabotCommand.class);

		commandManager.builder("xkcd").executor(xkcdCommand.class);
		commandManager.builder("c&h", "explosm", "cyanideandhappiness").executor(ExplosmCommand.class);
		commandManager.builder("rcg").executor(ExplosmRCGCommand.class);
		commandManager.builder("google").executor(GoogleCommand.class);
		commandManager.builder("math").executor(MathCommand.class);
		commandManager.builder("rand", "random", "rnd", "roll").executor(RollCommand.class);
		commandManager.builder("discordgold").executor(DiscordGoldCommand.class);
		commandManager.builder("tts", "texttospeech").executor(TextToSpeechCommand.class);
		commandManager.builder("getimage", "getmedia", "getshit").executor(GetMediaCommand.class);
		commandManager.builder("listimage", "listmedia", "listshit").executor(ListMediaCommand.class);
		commandManager.builder("cats", "cat", "getmecats").executor(CatsCommand.class);

//		commandManager.builder("kotlin_test").executor(KOTLIN_KotlinBase.class);
//		commandManager.builder("kotlin_xkcd").executor(KOTLIN_xkcdCommand.class);
//		commandManager.builder("test").executor(TestCommand.class);
		commandManager.builder("googleyeyes").executor(AVALON_GoogleyEyesCommand.class);
		commandManager.builder("youtube").executor(YoutubeCommand.class);
		
		commandManager.builder("joinchannel").executor(JoinChannelCommand.class);
		commandManager.builder("queue").executor(QueueCommand.class);
		commandManager.builder("volume").executor(VolumeCommand.class);
		commandManager.builder("play", "resume").executor(PlayCommand.class);
		commandManager.builder("leavechannel").executor(LeaveChannelCommand.class);
		commandManager.builder("nowplaying").executor(NowPlayingCommand.class);
		commandManager.builder("repeat").executor(RepeatCommand.class);
		commandManager.builder("skip").executor(SkipCommand.class);
		commandManager.builder("stop").executor(StopCommand.class);
		commandManager.builder("restart").executor(RestartCommand.class);
		commandManager.builder("resetmusic").executor(ResetCommand.class);
		
		commandManager.builder("reassigntoken", "rtoken").executor(ReassignTokenCommand.class);
		commandManager.builder("runjs", "javascript").executor(JavascriptCommand.class);
		commandManager.builder("reassignperm", "changeperm", "reassignpermission").executor(ReassignPermissionCommand.class);
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

	
	public GuildManager getGuildManager()
	{
		return manager;
	}

	
	public void messageEvent(MessageReceivedEvent event)
	{
		getCommandManager().callCommand(event);
	}

	
	public File getBaseFile()
	{
		return baseFile;
	}

	
	public String getBasePath()
	{
		return basePath;
	}

}
