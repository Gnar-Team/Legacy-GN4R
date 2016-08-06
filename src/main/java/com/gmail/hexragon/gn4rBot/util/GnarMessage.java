package com.gmail.hexragon.gn4rBot.util;

import net.dv8tion.jda.JDA;
import net.dv8tion.jda.entities.*;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.function.Consumer;

public class GnarMessage implements Message
{
	private final Message message;
	
	public GnarMessage(Message message)
	{
		this.message = message;
	}
	
	public Message replyRaw(String s)
	{
		return message.getChannel().sendMessage(s);
	}
	
	// Stylized reply
	public Message reply(String s)
	{
		return message.getChannel().sendMessage(message.getAuthor().getAsMention() + " ➜ " + s);
	}
	
	@Override
	public String getId()
	{
		return message.getId();
	}
	
	@Override
	public List<User> getMentionedUsers()
	{
		return message.getMentionedUsers();
	}
	
	@Override
	public boolean isMentioned(User user)
	{
		return message.isMentioned(user);
	}
	
	@Override
	public List<TextChannel> getMentionedChannels()
	{
		return message.getMentionedChannels();
	}
	
	@Override
	public List<Role> getMentionedRoles()
	{
		return message.getMentionedRoles();
	}
	
	@Override
	public boolean mentionsEveryone()
	{
		return message.mentionsEveryone();
	}
	
	@Override
	public OffsetDateTime getTime()
	{
		return message.getTime();
	}
	
	@Override
	public boolean isEdited()
	{
		return message.isEdited();
	}
	
	@Override
	public OffsetDateTime getEditedTimestamp()
	{
		return message.getEditedTimestamp();
	}
	
	@Override
	public User getAuthor()
	{
		return message.getAuthor();
	}
	
	@Override
	public String getContent()
	{
		return message.getContent();
	}
	
	@Override
	public String getRawContent()
	{
		return message.getRawContent();
	}
	
	@Override
	public String getStrippedContent()
	{
		return message.getStrippedContent();
	}
	
	@Override
	public boolean isPrivate()
	{
		return message.isPrivate();
	}
	
	@Override
	public String getChannelId()
	{
		return message.getChannelId();
	}
	
	@Override
	public MessageChannel getChannel()
	{
		return message.getChannel();
	}
	
	@Override
	public List<Attachment> getAttachments()
	{
		return message.getAttachments();
	}
	
	@Override
	public List<MessageEmbed> getEmbeds()
	{
		return message.getEmbeds();
	}
	
	@Override
	public boolean isTTS()
	{
		return message.isTTS();
	}
	
	@Override
	public Message updateMessage(String s)
	{
		return message.updateMessage(s);
	}
	
	@Override
	public void updateMessageAsync(String s, Consumer<Message> consumer)
	{
		message.updateMessageAsync(s, consumer);
	}
	
	@Override
	public void deleteMessage()
	{
		message.deleteMessage();
	}
	
	@Override
	public JDA getJDA()
	{
		return message.getJDA();
	}
	
	@Override
	public boolean isPinned()
	{
		return message.isPinned();
	}
	
	@Override
	public boolean pin()
	{
		return message.pin();
	}
	
	@Override
	public boolean unpin()
	{
		return message.unpin();
	}
	
	@Override
	public MessageType getType()
	{
		return message.getType();
	}
	
	@Override
	public List<Emote> getEmotes()
	{
		return message.getEmotes();
	}
}
