package com.gmail.hexragon.gn4rBot.command.fun;

import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandManager;
import net.dv8tion.jda.MessageBuilder;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

public class TextToSpeechCommand extends CommandExecutor
{
	public TextToSpeechCommand(CommandManager manager)
	{
		super(manager);
		setDescription("Text to speech fun.");
	}

	@Override
	public void execute(MessageReceivedEvent event, String[] args)
	{
		MessageBuilder builder = new MessageBuilder();
		builder.setTTS(true);
		builder.appendString(event.getMessage().getContent().replaceFirst(getCommandManager().getToken()+"tts ", ""));

		event.getChannel().sendMessage(builder.build());
	}
}
