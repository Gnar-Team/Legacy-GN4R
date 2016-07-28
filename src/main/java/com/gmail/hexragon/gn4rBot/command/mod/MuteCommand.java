package com.gmail.hexragon.gn4rBot.command.mod;

import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandManager;
import com.gmail.hexragon.gn4rBot.managers.users.PermissionLevel;
import net.dv8tion.jda.entities.User;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

public class MuteCommand extends CommandExecutor
{
	public MuteCommand()
	{
		
		setDescription("Mute/silence users from chat.");
		setUsage("(@user)");
		setPermission(PermissionLevel.BOT_COMMANDER);
	}
	
	@Override
	public void execute(MessageReceivedEvent event, String[] args)
	{
		User target = event.getMessage().getMentionedUsers().get(0);
		
		if (target == null)
		{
			event.getChannel().sendMessage(String.format("%s ➤ You did not mention a user.", event.getAuthor().getAsMention()));
			return;
		}
		
		getGnarGuild().mute(target);
		event.getChannel().sendMessage(String.format("%s ➤ You have muted %s.", event.getAuthor().getAsMention(), target.getAsMention()));
	}
}
