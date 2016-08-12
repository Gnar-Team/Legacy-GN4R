package com.gmail.hexragon.gn4rBot.managers.commands.annotations;

import com.gmail.hexragon.gn4rBot.managers.directMessage.PMManager;
import com.gmail.hexragon.gn4rBot.managers.guildMessage.GuildManager;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Makes sure so that commands that requires access to specific
 * PublicGnarGuild commands do not get registered in
 * PrivateGNarGuild command list.
 *
 * @see GuildManager
 * @see PMManager
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RequiresGuild
{
    
}
