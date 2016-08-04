package com.gmail.hexragon.gn4rBot.command.mod;

import com.gmail.hexragon.gn4rBot.managers.commands.Command;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.managers.users.PermissionLevel;
import net.dv8tion.jda.MessageHistory;
import net.dv8tion.jda.entities.Message;
import net.dv8tion.jda.entities.TextChannel;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Command(
		aliases = {"deletemessages"},
		usage = "(integer)",
		description = "Delete an amount of recent messages from the server.",
		permissionRequired = PermissionLevel.BOT_COMMANDER
)
public class DeleteMessagesCommand extends CommandExecutor
{
	@Override
	public void execute(Message message, String[] args)
	{
		if (args.length == 0)
		{
			message.getChannel().sendMessage(message.getAuthor().getAsMention() + " ➜ Insufficient amount of arguments.");
			return;
		}
		
		MessageHistory messageHistory = message.getChannel().getHistory();
		
		int amount = Integer.valueOf(args[0]);
		amount = Math.min(amount, 100);
		
		if (amount < 2)
		{
			message.getChannel().sendMessage(message.getAuthor().getAsMention() + " ➜ You need to delete 2 or more messages to use this command.");
			return;
		}
		
		List<Message> messages = messageHistory.retrieve(amount);
		
		if (args.length == 2)
		{
			if (args[1].contains("-content:"))
			{
				String targetWord = args[1].replaceFirst("-content:","");
				Set<Message> removeSet = messages.stream().filter(msg -> msg.getContent().toLowerCase().contains(targetWord.toLowerCase())).collect(Collectors.toSet());
				
				((TextChannel) message.getChannel()).deleteMessages(removeSet);
				message.getChannel().sendMessage(message.getAuthor().getAsMention() + " ➜ Attempted to delete `" + removeSet.size() + "` messages with the word `"+targetWord+"`.");
				return;
			}
		}
		
		((TextChannel) message.getChannel()).deleteMessages(messages);
		message.getChannel().sendMessage(message.getAuthor().getAsMention() + " ➜ Attempted to delete `" + messages.size() + "` messages.");
	}
}
