package com.gmail.hexragon.gn4rBot.command.fun;

import com.gmail.hexragon.gn4rBot.managers.commands.Command;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.util.GnarMessage;
import net.dv8tion.jda.MessageBuilder;

@Command(
		aliases = {"tts"},
		usage = "(string)",
		description = "Text to speech fun."
)
public class TextToSpeechCommand extends CommandExecutor
{
	@Override
	public void execute(GnarMessage message, String[] args)
	{
		if (args.length == 0)
		{
			message.reply("Please provide a query.");
			return;
		}
		
		MessageBuilder builder = new MessageBuilder();
		builder.setTTS(true);
		builder.appendString(message.getContent().replaceFirst(getCommandManager().getToken() + "tts ", ""));
		
		message.getChannel().sendMessage(builder.build());
	}
}
