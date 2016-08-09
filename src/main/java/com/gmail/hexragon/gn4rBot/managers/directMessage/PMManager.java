package com.gmail.hexragon.gn4rBot.managers.directMessage;

import com.gmail.hexragon.gn4rBot.command.admin.ArgsTestCommand;
import com.gmail.hexragon.gn4rBot.command.admin.DiagnosticsCommand;
import com.gmail.hexragon.gn4rBot.command.admin.JavascriptCommand;
import com.gmail.hexragon.gn4rBot.command.admin.ThrowError;
import com.gmail.hexragon.gn4rBot.command.ai.PrivateCleverbotCommand;
import com.gmail.hexragon.gn4rBot.command.fun.*;
import com.gmail.hexragon.gn4rBot.command.games.GameLookupCommand;
import com.gmail.hexragon.gn4rBot.command.games.LeagueLookupCommand;
import com.gmail.hexragon.gn4rBot.command.games.OverwatchLookupCommand;
import com.gmail.hexragon.gn4rBot.command.general.*;
import com.gmail.hexragon.gn4rBot.command.media.*;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandManager;
import com.gmail.hexragon.gn4rBot.managers.servers.GnarGuild;
import com.gmail.hexragon.gn4rBot.managers.servers.ServerManager;
import com.gmail.hexragon.gn4rBot.managers.users.UserManager;
import net.dv8tion.jda.entities.Guild;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

public class PMManager implements GnarGuild
{
	private final String accessID;
	private final CommandManager commandManager;
	private final ServerManager manager;
	
	public PMManager(String accessID, ServerManager manager)
	{
		this.accessID = accessID;
		this.manager = manager;
		this.commandManager = new PMCommandManager(this);
		
		defaultSetup();
	}
	
	private void defaultSetup()
	{
		commandManager.registerCommand(HelpCommand.class);
		commandManager.registerCommand(BotInfoCommand.class);
		//commandManager.registerCommand(WhoIsCommand.class); // does not work in PM
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
		
		commandManager.registerCommand(PrivateCleverbotCommand.class); //individual cbots per users
		//commandManager.registerCommand(PandorabotCommand.class);
		//commandManager.registerCommand(TextToSpeechCommand.class); //DOESNT WORK
		
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
		
		commandManager.registerCommand(JavascriptCommand.class);
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
	public Guild getGuild()
	{
		return null;
	}
	
	@Override
	public String getBasePath()
	{
		return null;
	}
	
	@Override
	public UserManager getUserManager()
	{
		return null;
	}
}
