package com.gmail.hexragon.gn4rBot.command.fun;

import com.gmail.hexragon.gn4rBot.managers.commands.Command;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.util.GnarMessage;
import org.apache.commons.lang3.StringUtils;

import java.util.StringJoiner;

@Command(
		aliases = {"poop"},
		description = "Shit your heart out."
)
public class PoopCommand extends CommandExecutor
{
	@Override
	public void execute(GnarMessage message, String[] args)
	{
		String poop = StringUtils.join(args, " ");
		
		StringJoiner joiner = new StringJoiner("\n","```\n","```");
		
		joiner.add("░░░░░░░░░░░█▀▀░░█░░░░░░");
		joiner.add("░░░░░░▄▀▀▀▀░░░░░█▄▄░░░░");
		joiner.add("░░░░░░█░█░░░░░░░░░░▐░░░");
		joiner.add("░░░░░░▐▐░░░░░░░░░▄░▐░░░");
		joiner.add("░░░░░░█░░░░░░░░▄▀▀░▐░░░");
		joiner.add("░░░░▄▀░░░░░░░░▐░▄▄▀░░░░");
		joiner.add("░░▄▀░░░▐░░░░░█▄▀░▐░░░░░");
		joiner.add("░░█░░░▐░░░░░░░░▄░▌░░░░░");
		joiner.add("░░░█▄░░▀▄░░░░▄▀█░▌░░░░░");
		joiner.add("░░░▌▐▀▀▀░▀▀▀▀░░█░▌░░░░░");
		joiner.add("░░▐▌▐▄░░▀▄░░░░░█░█▄▄░░░");
		
		
		StringBuilder poopArt = new StringBuilder("░░░▀▀░▄███▄▄░░░▀▄▄▄▀░░░");
		
		for (int i = 0; i < poop.length(); i++)
		{
			try
			{
				poopArt.setCharAt(7 + i, poop.charAt(i));
			}
			catch (Exception e)
			{
				message.reply("Poop is too big to shit out.");
				return;
			}
		}
		
		joiner.add(poopArt);
		joiner.add("░░░░░░░░░░░░░░░░░░░░░░░");

		message.replyRaw(joiner.toString());
	}
}
