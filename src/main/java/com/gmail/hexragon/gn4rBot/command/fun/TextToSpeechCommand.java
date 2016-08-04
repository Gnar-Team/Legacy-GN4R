package com.gmail.hexragon.gn4rBot.command.fun;

import com.gmail.hexragon.gn4rBot.managers.commands.Command;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import net.dv8tion.jda.MessageBuilder;
import net.dv8tion.jda.entities.Message;

@Command(
		aliases = {"tts"},
		usage = "(string)",
		description = "Text to speech fun."
)
public class TextToSpeechCommand extends CommandExecutor
{
	@Override
	public void execute(Message message, String[] args)
	{
		if (args.length == 0)
		{
			message.getChannel().sendMessage(String.format("%s ➜ Please provide a query.", message.getAuthor().getAsMention()));
			return;
		}
		
		MessageBuilder builder = new MessageBuilder();
		builder.setTTS(true);
		builder.appendString(message.getContent().replaceFirst(getCommandManager().getToken() + "tts ", ""));
		
		message.getChannel().sendMessage(builder.build());
	}
}
