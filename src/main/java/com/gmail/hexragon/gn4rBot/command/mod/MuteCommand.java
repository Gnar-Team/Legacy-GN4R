package com.gmail.hexragon.gn4rBot.command.mod;

import com.gmail.hexragon.gn4rBot.managers.commands.Command;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.managers.users.PermissionLevel;
import net.dv8tion.jda.entities.Message;
import net.dv8tion.jda.entities.User;

@Command(
		aliases = {"mute"},
		usage = "(@user)",
		description = "Mute/silence users from chat.",
		permissionRequired = PermissionLevel.BOT_COMMANDER
)
public class MuteCommand extends CommandExecutor
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
		
		getGnarGuild().mute(target);
		message.getChannel().sendMessage(String.format("%s ➤ You have muted %s.", message.getAuthor().getAsMention(), target.getAsMention()));
	}
}
