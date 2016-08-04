package com.gmail.hexragon.gn4rBot.command.mod;

import com.gmail.hexragon.gn4rBot.managers.commands.Command;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.managers.users.PermissionLevel;
import net.dv8tion.jda.entities.Message;
import net.dv8tion.jda.entities.User;

@Command(
		aliases = {"ban"},
		usage = "(@user)",
		description = "Ban users from the server.",
		permissionRequired = PermissionLevel.BOT_COMMANDER
)
public class BanCommand extends CommandExecutor
{
	@Override
	public void execute(Message message, String[] args)
	{
		User target = message.getMentionedUsers().get(0);
		
		if (target == null)
		{
			message.getChannel().sendMessage(String.format("%s ➜ You did mention a user.", message.getAuthor().getAsMention()));
			return;
		}
		
		getGnarGuild().ban(target, 0);
		message.getChannel().sendMessage(String.format("%s ➜ You have banned %s.", message.getAuthor().getAsMention(), target.getAsMention()));
	}
}
