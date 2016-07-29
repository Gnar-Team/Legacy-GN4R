package com.gmail.hexragon.gn4rBot.command.mod;

import com.gmail.hexragon.gn4rBot.managers.commands.Command;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.managers.users.PermissionLevel;
import net.dv8tion.jda.entities.Message;
import net.dv8tion.jda.entities.User;

@Command(
		aliases = {"unmute"},
		usage = "(@user)",
		description = "Allow users to use chat again.",
		permissionRequired = PermissionLevel.BOT_COMMANDER
)
public class UnmuteCommand extends CommandExecutor
{
	@Override
	public void execute(Message message, String[] args)
	{
		
		User target = message.getMentionedUsers().get(0);
		
		if (target == null)
		{
			message.getChannel().sendMessage(String.format("%s ➤ You did not mention a user.", message.getAuthor().getAsMention()));
			return;
		}
		
		getGnarGuild().unmute(target);
		message.getChannel().sendMessage(String.format("%s ➤ You have unmuted %s.", message.getAuthor().getAsMention(), target.getAsMention()));
	}
}
