package com.gmail.hexragon.gn4rBot.command.mod;

import com.gmail.hexragon.gn4rBot.managers.commands.Command;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.managers.users.PermissionLevel;
import com.gmail.hexragon.gn4rBot.util.GnarMessage;
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
	public void execute(GnarMessage message, String[] args)
	{
		if (message.getMentionedUsers().size() < 1)
		{
			message.reply("You did not mention a user.");
			return;
		}
		
		User target = message.getMentionedUsers().get(0);
		
		getGnarGuild().mute(target);
		message.reply("You have muted "+target.getAsMention()+".");
	}
}
