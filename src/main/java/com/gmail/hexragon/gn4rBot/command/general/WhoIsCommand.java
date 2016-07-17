package com.gmail.hexragon.gn4rBot.command.general;

import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandManager;
import net.dv8tion.jda.entities.User;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

import java.util.StringJoiner;

public class WhoIsCommand extends CommandExecutor
{
	public WhoIsCommand(CommandManager manager)
	{
		super(manager);
		setUsage("whois (user)");
	}

	@Override
	public void execute(MessageReceivedEvent event, String[] args)
	{
		User user = event.getMessage().getMentionedUsers().get(0);

		if (user == null)
		{
			event.getChannel().sendMessage("You did not mention a valid user.");
			return;
		}

		StringBuilder mainBuilder = new StringBuilder();


		StringJoiner metaBuilder = new StringJoiner("\n");
		metaBuilder.add("[Main Information]");
		metaBuilder.add("   ├─[ID]                   "+user.getId());
		metaBuilder.add("   ├─[Name]                 "+user.getUsername());
		metaBuilder.add("   ├─[Nickname]             "+getGnarGuild().getGuild().getNicknameForUser(user));
		metaBuilder.add("   ├─[Game]                 "+user.getCurrentGame().getName());
		metaBuilder.add("   ├─[Avatar]               "+user.getAvatarUrl());
		metaBuilder.add("   ├─[Discriminator]        "+user.getDiscriminator());
		//metaBuilder.add("   ├─[Hashcode]             "+String.valueOf(user.hashCode()));
		metaBuilder.add("   ├─[Bot Status]           "+String.valueOf(user.isBot()).toUpperCase());
		metaBuilder.add("   |");

		mainBuilder.append(metaBuilder.toString()).append("\n");

		mainBuilder.append("   ├─[Roles]\n");

		StringBuilder rolesBuilder = new StringBuilder();
		getGnarGuild().getGuild().getRolesForUser(user).stream()
				.filter(role -> !rolesBuilder.toString().contains(role.getId()))
				.forEach(role -> rolesBuilder.append("   |    ├─[").append(role.getName()).append("]\n"));

		int lastIndex = rolesBuilder.toString().lastIndexOf("├");
		if(lastIndex >= 0) rolesBuilder.replace(lastIndex, lastIndex+1, "└");

		mainBuilder.append(rolesBuilder.toString());

		mainBuilder.append("   |\n   └─[Gn4r Perm]            ").append(getCommandManager().getUserManager().getGnarUser(user).getPermission().toString().replaceAll("_", " "));

		event.getChannel().sendMessage("```xl\n"+mainBuilder.toString().replaceAll("null", "None")+"```");
	}
}
