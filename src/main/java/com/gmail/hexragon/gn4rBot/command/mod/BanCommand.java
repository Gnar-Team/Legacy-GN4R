package com.gmail.hexragon.gn4rBot.command.mod;

import com.gmail.hexragon.gn4rBot.managers.commands.Command;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.managers.users.PermissionLevel;
import com.gmail.hexragon.gn4rBot.util.GnarMessage;
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
	public void execute(GnarMessage message, String[] args)
	{
		if (message.getMentionedUsers().size() < 1)
		{
			message.reply("You did not mention a user.");
			return;
		}
		
		User target = message.getMentionedUsers().get(0);
		
		getGnarGuild().ban(target, 0);
		message.reply("You have banned "+target.getAsMention()+".");
	}
}
