package com.gmail.hexragon.gn4rBot.command.mod;

import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandManager;
import com.gmail.hexragon.gn4rBot.managers.users.PermissionLevel;
import net.dv8tion.jda.entities.User;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

public class UnmuteCommand extends CommandExecutor
{
	public UnmuteCommand()
	{
		
		setDescription("Allow users to use chat again.");
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
		
		getGnarGuild().unmute(target);
		event.getChannel().sendMessage(String.format("%s ➤ You have unmuted %s.", event.getAuthor().getAsMention(), target.getAsMention()));
	}
}
