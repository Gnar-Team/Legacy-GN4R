package com.gmail.hexragon.gn4rBot.command.mod;

import com.gmail.hexragon.gn4rBot.managers.commands.Command;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.managers.users.PermissionLevel;
import net.dv8tion.jda.entities.Message;
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
	public void execute(Message message, String[] args)
	{
		if (args.length == 0)
		{
			message.getChannel().sendMessage(message.getAuthor().getAsMention() + " ➜ Insufficient amount of arguments.");
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
			message.getChannel().sendMessage(message.getAuthor().getAsMention() + " ➜ That is not a valid user ID.");
			return;
		}
		
		getGnarGuild().unBan(target);
		message.getChannel().sendMessage(String.format("%s ➜ You have unbanned %s.", message.getAuthor().getAsMention(), target.getAsMention()));
	}
}
