package com.gmail.hexragon.gn4rBot.command.mod;

import com.gmail.hexragon.gn4rBot.managers.commands.Command;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.managers.users.PermissionLevel;
import com.gmail.hexragon.gn4rBot.util.GnarMessage;
import net.dv8tion.jda.entities.User;

import java.util.List;

@Command(
		aliases = {"unban"},
		usage = "(user-id)",
		description = "Unban users.",
		permissionRequired = PermissionLevel.BOT_COMMANDER
)
public class UnbanCommand extends CommandExecutor
{
	@Override
	public void execute(GnarMessage message, String[] args)
	{
		if (args.length == 0)
		{
			message.reply("You did not provide a user ID.");
			return;
		}
		
		List<User> bannedList = getGnarGuild().getBans();
		
		User target = null;
		
		for (User user : bannedList)
		{
			if (user.getId().equals(args[0]))
			{
				target = user;
			}
		}
		
		if (target == null)
		{
			message.reply("That is not a valid user ID.");
			return;
		}
		
		getGnarGuild().unBan(target);
		message.reply("You have unbanned "+target.getAsMention()+".");
	}
}
