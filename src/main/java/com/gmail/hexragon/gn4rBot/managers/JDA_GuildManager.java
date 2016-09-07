package com.gmail.hexragon.gn4rBot.managers;

import net.dv8tion.jda.Permission;
import net.dv8tion.jda.Region;
import net.dv8tion.jda.entities.*;
import net.dv8tion.jda.entities.impl.JDAImpl;
import net.dv8tion.jda.entities.impl.UserImpl;
import net.dv8tion.jda.exceptions.GuildUnavailableException;
import net.dv8tion.jda.exceptions.PermissionException;
import net.dv8tion.jda.requests.Requester;
import net.dv8tion.jda.utils.AvatarUtil;
import net.dv8tion.jda.utils.PermissionUtil;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

public class JDA_GuildManager
{
    private enum Timeout
    {
        SECONDS_60(60),
        SECONDS_300(300),
        SECONDS_900(900),
        SECONDS_1800(1800),
        SECONDS_3600(3600);
        
        private final int seconds;
        Timeout(int seconds)
        {
            this.seconds = seconds;
        }
        
        public int getSeconds()
        {
            return seconds;
        }
        
        @Override
        public String toString()
        {
            return "" + seconds;
        }
    }
    
    private final Guild guild;
    
    private Timeout timeout = null;
    private String name = null;
    private Region region = null;
    private AvatarUtil.Avatar icon = null;
    private Guild.VerificationLevel verificationLevel = null;
    private String afkChannelId;
    
    private final Map<User, Set<Role>> addedRoles = new HashMap<>();
    private final Map<User, Set<Role>> removedRoles = new HashMap<>();
    
    JDA_GuildManager(Guild guild)
    {
        this.guild = guild;
        if (guild != null) this.afkChannelId = guild.getAfkChannelId();
    }
    
    public Guild getGuild()
    {
        return guild;
    }
    
    public JDA_GuildManager setName(String name)
    {
        if (!guild.isAvailable())
        {
            throw new GuildUnavailableException();
        }
        checkPermission(Permission.MANAGE_SERVER);
        
        if (guild.getName().equals(name))
        {
            this.name = null;
        }
        else
        {
            this.name = name;
        }
        return this;
    }
  
    public JDA_GuildManager setRegion(Region region)
    {
        if (!guild.isAvailable())
        {
            throw new GuildUnavailableException();
        }
        checkPermission(Permission.MANAGE_SERVER);
        
        if (region == guild.getRegion() || region == Region.UNKNOWN)
        {
            this.region = null;
        }
        else
        {
            this.region = region;
        }
        return this;
    }
    
 
    public JDA_GuildManager setIcon(AvatarUtil.Avatar avatar)
    {
        if (!guild.isAvailable())
        {
            throw new GuildUnavailableException();
        }
        checkPermission(Permission.MANAGE_SERVER);
        
        this.icon = avatar;
        return this;
    }
    
    public JDA_GuildManager setAfkChannel(VoiceChannel channel)
    {
        if (!guild.isAvailable())
        {
            throw new GuildUnavailableException();
        }
        checkPermission(Permission.MANAGE_SERVER);
        
        if (channel != null && channel.getGuild() != guild)
        {
            throw new IllegalArgumentException("Given VoiceChannel is not member of modifying Guild");
        }
        this.afkChannelId = channel == null ? null : channel.getId();
        return this;
    }
    
   
    public JDA_GuildManager setAfkTimeout(Timeout timeout)
    {
        if (!guild.isAvailable())
        {
            throw new GuildUnavailableException();
        }
        checkPermission(Permission.MANAGE_SERVER);
        
        this.timeout = timeout;
        return this;
    }
   
    public JDA_GuildManager addRoleToUser(User user, Role... roles)
    {
        if (!guild.isAvailable())
        {
            throw new GuildUnavailableException();
        }
        checkPermission(Permission.MANAGE_ROLES);
        for (Role role : roles)
        {
            checkPosition(role);
        }
        
        Set<Role> addRoles = addedRoles.get(user);
        if (addRoles == null)
        {
            addRoles = new HashSet<>();
            addedRoles.put(user, addRoles);
        }
        Set<Role> removeRoles = removedRoles.get(user);
        if (removeRoles == null)
        {
            removeRoles = new HashSet<>();
            removedRoles.put(user, removeRoles);
        }
        for (Role role : roles)
        {
            if(guild.getPublicRole().equals(role))
                return this;
            
            if (removeRoles.contains(role))
                removeRoles.remove(role);
            
            addRoles.add(role);
        }
        return this;
    }
    
  
    public JDA_GuildManager removeRoleFromUser(User user, Role... roles)
    {
        if (!guild.isAvailable())
        {
            throw new GuildUnavailableException();
        }
        checkPermission(Permission.MANAGE_ROLES);
        for (Role role : roles)
        {
            checkPosition(role);
        }
        
        Set<Role> addRoles = addedRoles.get(user);
        if (addRoles == null)
        {
            addRoles = new HashSet<>();
            addedRoles.put(user, addRoles);
        }
        Set<Role> removeRoles = removedRoles.get(user);
        if (removeRoles == null)
        {
            removeRoles = new HashSet<>();
            removedRoles.put(user, removeRoles);
        }
        for (Role role : roles)
        {
            if(guild.getPublicRole().equals(role))
                return this;
            
            if (addRoles.contains(role))
                addRoles.remove(role);
            
            removeRoles.add(role);
        }
        return this;
    }
    
  
    public JDA_GuildManager setVerificationLevel(Guild.VerificationLevel level)
    {
        if (!guild.isAvailable())
        {
            throw new GuildUnavailableException();
        }
        checkPermission(Permission.MANAGE_SERVER);
        
        if (guild.getVerificationLevel() == level)
        {
            this.verificationLevel = null;
        }
        else
        {
            this.verificationLevel = level;
        }
        return this;
    }
   
    public void reset() {
        name = null;
        region = null;
        timeout = null;
        icon = null;
        verificationLevel = null;
        afkChannelId = guild.getAfkChannelId();
        addedRoles.clear();
        removedRoles.clear();
    }
    
    
    public void update()
    {
        if (!guild.isAvailable())
        {
            throw new GuildUnavailableException();
        }
        
        if (name != null || region != null || timeout != null || icon != null || !StringUtils.equals(afkChannelId, guild.getAfkChannelId()) || verificationLevel != null)
        {
            checkPermission(Permission.MANAGE_SERVER);
            
            JSONObject frame = getFrame();
            if(name != null)
                frame.put("name", name);
            if(region != null)
                frame.put("region", region.getKey());
            if(timeout != null)
                frame.put("afk_timeout", timeout.getSeconds());
            if(icon != null)
                frame.put("icon", icon == AvatarUtil.DELETE_AVATAR ? JSONObject.NULL : icon.getEncoded());
            if(!StringUtils.equals(afkChannelId, guild.getAfkChannelId()))
                frame.put("afk_channel_id", afkChannelId == null ? JSONObject.NULL : afkChannelId);
            if(verificationLevel != null)
                frame.put("verification_level", verificationLevel.getKey());
            update(frame);
        }
        
        if (addedRoles.size() > 0)
        {
            checkPermission(Permission.MANAGE_ROLES);
            
            for (User user : addedRoles.keySet())
            {
                List<Role> roles = guild.getRolesForUser(user);
                List<String> roleIds = new LinkedList<>();
                roles.forEach(r -> roleIds.add(r.getId()));
                
                addedRoles.get(user).stream().filter(role -> !roleIds.contains(role.getId())).forEach(role -> roleIds.add(role.getId()));
                removedRoles.get(user).stream().filter(role -> roleIds.contains(role.getId())).forEach(role -> roleIds.remove(role.getId()));
                
                ((JDAImpl) guild.getJDA()).getRequester().patch(
                        Requester.DISCORD_API_PREFIX + "guilds/" + guild.getId() + "/members/" + user.getId(),
                        new JSONObject().put("roles", roleIds));
                
            }
            addedRoles.clear();
            removedRoles.clear();
        }
    }
    
    /**
     * Changes a user's nickname in this guild.
     * The nickname is visible to all users of this guild.
     * This requires the correct Permissions to perform
     * ({@link net.dv8tion.jda.Permission#NICKNAME_MANAGE NICKNAME_MANAGE} for others+self and
     * {@link net.dv8tion.jda.Permission#NICKNAME_CHANGE NICKNAME_CHANGE} for only self).
     *
     * @param user
     *      The user for which the nickname should be changed.
     * @param nickname
     *      The new nickname of the user, or null/"" to reset
     * @throws net.dv8tion.jda.exceptions.GuildUnavailableException
     *      if the guild is temporarily unavailable
     */
    public void setNickname(User user, String nickname)
    {
        if (!guild.isAvailable())
        {
            throw new GuildUnavailableException();
        }
        
        if(user == guild.getJDA().getSelfInfo())
        {
            if(!PermissionUtil.checkPermission(user, Permission.NICKNAME_CHANGE, guild) && !PermissionUtil.checkPermission(user, Permission.NICKNAME_MANAGE, guild))
                throw new PermissionException(Permission.NICKNAME_CHANGE, "You neither have NICKNAME_CHANGE nor NICKNAME_MANAGE permission!");
        }
        else
        {
            checkPermission(Permission.NICKNAME_MANAGE);
            checkPosition(user);
        }
        
        if (nickname == null)
            nickname = "";
        
        String url = Requester.DISCORD_API_PREFIX + "guilds/" + guild.getId() + "/members/"
                + (user == guild.getJDA().getSelfInfo() ? "@me/nick" : user.getId());
        
        ((JDAImpl) guild.getJDA()).getRequester()
                .patch(url, new JSONObject().put("nick", nickname));
    }
    
    /**
     * Used to move a {@link net.dv8tion.jda.entities.User User} from one {@link net.dv8tion.jda.entities.VoiceChannel VoiceChannel}
     * to another {@link net.dv8tion.jda.entities.VoiceChannel VoiceChannel}.<br>
     * As a note, you cannot move a User that isn't already in a VoiceChannel. Also they must be in a VoiceChannel
     * in the same Guild as the one that you are moving them to.
     *
     * @param user
     *          The {@link net.dv8tion.jda.entities.User User} that you are moving.
     * @param voiceChannel
     *          The destination {@link net.dv8tion.jda.entities.VoiceChannel VoiceChannel} to which the user is being
     *          moved to.
     * @throws java.lang.IllegalStateException
     *          If the User isn't currently in a VoiceChannel in this Guild.
     * @throws java.lang.IllegalArgumentException
     *          <ul>
     *              <li>If the provided User is null.</li>
     *              <li>If the provided VoiceChannel is null.</li>
     *              <li>If the provided VoiceChannel isn't part of this {@link net.dv8tion.jda.entities.Guild Guild}</li>
     *          </ul>
     * @throws net.dv8tion.jda.exceptions.PermissionException
     *          <ul>
     *              <li>If this account doesn't have {@link Permission#VOICE_MOVE_OTHERS} in the VoiceChannel that
     *                  the User is currently in.</li>
     *              <li>If this account <b>AND</b> the User being moved don't have
     *                  {@link Permission#VOICE_CONNECT} for the destination VoiceChannel.</li>
     *          </ul>
     */
    public void moveVoiceUser(User user, VoiceChannel voiceChannel)
    {
        if (user == null)
            throw new IllegalArgumentException("Provided User was null. Cannot determine which User to move when User is null!");
        if (voiceChannel == null)
            throw new IllegalArgumentException("Provided VoiceChannel was null. " +
                    "Cannot determine which channel to move the User to because VoiceChannel is null!");
        if (!voiceChannel.getGuild().getId().equals(guild.getId()))
            throw new IllegalArgumentException("Cannot move a User to a VoiceChannel that isn't part of this Guild!");
        
        VoiceStatus status  = guild.getVoiceStatusOfUser(user);
        if (!status.inVoiceChannel())
            throw new IllegalStateException("You cannot move a User who isn't in a VoiceChannel!");
        
        if (!PermissionUtil.checkPermission(guild.getJDA().getSelfInfo(), Permission.VOICE_MOVE_OTHERS, status.getChannel()))
            throw new PermissionException(Permission.VOICE_MOVE_OTHERS, "This account does not have Permission to MOVE_OTHERS from the currently VoiceChannel");
        
        if (!PermissionUtil.checkPermission(guild.getJDA().getSelfInfo(), Permission.VOICE_CONNECT, voiceChannel)
                && !PermissionUtil.checkPermission(user, Permission.VOICE_CONNECT, voiceChannel))
            throw new PermissionException(Permission.VOICE_CONNECT,
                    "Neither this account nor the User that is attempting to be moved have the VOICE_CONNECT permission " +
                            "for the destination VoiceChannel, so the move cannot be done.");
        
        ((JDAImpl) guild.getJDA()).getRequester().patch(
                Requester.DISCORD_API_PREFIX + "guilds/" + guild.getId() + "/members/" + user.getId(),
                new JSONObject().put("channel_id", voiceChannel.getId()));
    }
    
    /**
     * This method will either prune (kick) all members who were offline for at least <i>days</i> days,
     * or just return the number of members that would be pruned.
     *
     * @param days
     *      Minimum number of days since a user has been offline to get affected.
     * @param doKick
     *      Whether or not these members should actually get kicked or not
     * @return
     *      The number of users that have been / would get pruned
     * @throws PermissionException
     *      If the JDA-account doesn't have the {@link net.dv8tion.jda.Permission#KICK_MEMBERS KICK_MEMBER Permission}
     */
    public int prune(int days, boolean doKick)
    {
        if (!PermissionUtil.checkPermission(guild.getJDA().getSelfInfo(), Permission.KICK_MEMBERS, guild))
            throw new PermissionException(Permission.KICK_MEMBERS);
        JSONObject returned;
        if (doKick)
        {
            returned = ((JDAImpl) guild.getJDA()).getRequester().post(Requester.DISCORD_API_PREFIX + "guilds/" + guild.getId() + "/prune?days=" + days, new JSONObject()).getObject();
        }
        else
        {
            returned = ((JDAImpl) guild.getJDA()).getRequester().get(Requester.DISCORD_API_PREFIX + "guilds/" + guild.getId() + "/prune?days=" + days).getObject();
        }
        return returned.getInt("pruned");
    }
    
    /**
     * Kicks a {@link net.dv8tion.jda.entities.User User} from the {@link net.dv8tion.jda.entities.Guild Guild}.
     * This change will be applied immediately.<br>
     * <p>
     * <b>Note:</b> {@link net.dv8tion.jda.entities.Guild#getUsers()} will still contain the {@link net.dv8tion.jda.entities.User User}
     * until Discord sends the {@link net.dv8tion.jda.events.guild.member.GuildMemberLeaveEvent GuildMemberLeaveEvent}.
     *
     * @param user
     *          The {@link net.dv8tion.jda.entities.User User} to kick from the from the {@link net.dv8tion.jda.entities.Guild Guild}.
     * @throws net.dv8tion.jda.exceptions.GuildUnavailableException
     *      if the guild is temporarily unavailable
     */
    public void kick(User user)
    {
        if (!guild.isAvailable())
        {
            throw new GuildUnavailableException();
        }
        checkPermission(Permission.KICK_MEMBERS);
        checkPosition(user);
        
        ((JDAImpl) guild.getJDA()).getRequester().delete(Requester.DISCORD_API_PREFIX + "guilds/"
                + guild.getId() + "/members/" + user.getId());
    }
    
    /**
     * Kicks the {@link net.dv8tion.jda.entities.User User} specified by the userId from the from the {@link net.dv8tion.jda.entities.Guild Guild}.
     * This change will be applied immediately.
     * <p>
     * <b>Note:</b> {@link net.dv8tion.jda.entities.Guild#getUsers()} will still contain the {@link net.dv8tion.jda.entities.User User}
     * until Discord sends the {@link net.dv8tion.jda.events.guild.member.GuildMemberLeaveEvent GuildMemberLeaveEvent}.
     *
     * @param userId
     *          The id of the {@link net.dv8tion.jda.entities.User User} to kick from the from the {@link net.dv8tion.jda.entities.Guild Guild}.
     * @throws net.dv8tion.jda.exceptions.GuildUnavailableException
     *      if the guild is temporarily unavailable
     */
    public void kick(String userId)
    {
        User user = guild.getJDA().getUserById(userId);
        if(user != null)
            kick(user);
    }
    
    /**
     * Bans a {@link net.dv8tion.jda.entities.User User} and deletes messages sent by the user
     * based on the amount of delDays.<br>
     * If you wish to ban a user without deleting any messages, provide delDays with a value of 0.
     * This change will be applied immediately.
     * <p>
     * <b>Note:</b> {@link net.dv8tion.jda.entities.Guild#getUsers()} will still contain the {@link net.dv8tion.jda.entities.User User}
     * until Discord sends the {@link net.dv8tion.jda.events.guild.member.GuildMemberLeaveEvent GuildMemberLeaveEvent}.
     *
     * @param user
     *          The {@link net.dv8tion.jda.entities.User User} to ban.
     * @param delDays
     *          The history of messages, in days, that will be deleted.
     * @throws net.dv8tion.jda.exceptions.GuildUnavailableException
     *      if the guild is temporarily unavailable
     */
    public void ban(User user, int delDays)
    {
        if (!guild.isAvailable())
        {
            throw new GuildUnavailableException();
        }
        checkPermission(Permission.BAN_MEMBERS);
        
        if (guild.getUsers().contains(user)) // If user is in guild. Check if we are able to ban.
            checkPosition(user);
        
        ((JDAImpl) guild.getJDA()).getRequester().put(Requester.DISCORD_API_PREFIX + "guilds/"
                + guild.getId() + "/bans/" + user.getId() + (delDays > 0 ? "?delete-message-days=" + delDays : ""), new JSONObject());
    }
    
    /**
     * Bans the {@link net.dv8tion.jda.entities.User User} specified by the userId and deletes messages sent by the user
     * based on the amount of delDays.<br>
     * If you wish to ban a user without deleting any messages, provide delDays with a value of 0.
     * This change will be applied immediately.
     * <p>
     * <b>Note:</b> {@link net.dv8tion.jda.entities.Guild#getUsers()} will still contain the {@link net.dv8tion.jda.entities.User User}
     * until Discord sends the {@link net.dv8tion.jda.events.guild.member.GuildMemberLeaveEvent GuildMemberLeaveEvent}.
     *
     * @param userId
     *          The id of the {@link net.dv8tion.jda.entities.User User} to ban.
     * @param delDays
     *          The history of messages, in days, that will be deleted.
     * @throws net.dv8tion.jda.exceptions.GuildUnavailableException
     *      if the guild is temporarily unavailable
     * @throws IllegalArgumentException
     *      if user does not exist
     */
    public void ban(String userId, int delDays)
    {
        if (!guild.isAvailable())
        {
            throw new GuildUnavailableException();
        }
        User user = guild.getJDA().getUserById(userId);
        if (user != null) // We have to check whether we are able to ban the user that is cached.
        {
            ban(user, delDays);
            return;
        }
        checkPermission(Permission.BAN_MEMBERS);
        
        Requester.Response response = ((JDAImpl) guild.getJDA()).getRequester().put(Requester.DISCORD_API_PREFIX + "guilds/"
                + guild.getId() + "/bans/" + userId + (delDays > 0 ? "?delete-message-days=" + delDays : ""), new JSONObject());
        if (response.isOk())
            return;
        if (response.code == 404)
            throw new IllegalArgumentException("User with id \"" + userId + "\" does not exist.");
        JDAImpl.LOG.fatal("Something went wrong trying to ban a user by id: " + response.toString());
    }
    
    /**
     * Deafens a {@link net.dv8tion.jda.entities.User User} in this {@link net.dv8tion.jda.entities.Guild Guild}.
     * Requires the {@link net.dv8tion.jda.Permission#VOICE_DEAF_OTHERS VOICE_DEAF_OTHERS} permission.
     *
     * @param user
     *      The user who should be deafened.
     * @throws net.dv8tion.jda.exceptions.GuildUnavailableException
     *      if the guild is temporarily unavailable
     * @see JDA_GuildManager#undeafen(User)
     */
    public void deafen(User user)
    {
        this.deafen(user, true);
    }
    
    /**
     * Mutes a {@link net.dv8tion.jda.entities.User User} in this {@link net.dv8tion.jda.entities.Guild Guild}.
     * Requires the {@link net.dv8tion.jda.Permission#VOICE_MUTE_OTHERS VOICE_MUTE_OTHERS} permission.
     *
     * @param user
     *      The user who should be muted.
     * @throws net.dv8tion.jda.exceptions.GuildUnavailableException
     *      if the guild is temporarily unavailable
     * @see JDA_GuildManager#unmute(User)
     */
    public void mute(User user)
    {
        this.mute(user, true);
    }
    
    /**
     * Gets an unmodifiable list of the currently banned {@link net.dv8tion.jda.entities.User Users}.<br>
     * If you wish to ban or unban a user, please use one of the ban or unban methods of this Manager
     *
     * @return
     *      unmodifiable list of currently banned Users
     * @throws net.dv8tion.jda.exceptions.GuildUnavailableException
     *      if the guild is temporarily unavailable
     */
    public List<User> getBans()
    {
        if (!guild.isAvailable())
        {
            throw new GuildUnavailableException();
        }
        checkPermission(Permission.BAN_MEMBERS);
        List<User> bans = new LinkedList<>();
        JSONArray bannedArr = ((JDAImpl) guild.getJDA()).getRequester().get(Requester.DISCORD_API_PREFIX + "guilds/" + guild.getId() + "/bans").getArray();
        for (int i = 0; i < bannedArr.length(); i++)
        {
            JSONObject userObj = bannedArr.getJSONObject(i).getJSONObject("user");
            User u = guild.getJDA().getUserById(userObj.getString("id"));
            if (u != null)
            {
                bans.add(u);
            }
            else
            {
                //Create user here, instead of using the EntityBuilder (don't want to add users to registry)
                bans.add(new UserImpl(userObj.getString("id"), ((JDAImpl) guild.getJDA()))
                        .setUserName(userObj.getString("username"))
                        .setDiscriminator(userObj.get("discriminator").toString())
                        .setAvatarId(userObj.isNull("avatar") ? null : userObj.getString("avatar")));
            }
        }
        return Collections.unmodifiableList(bans);
    }
    
    /**
     * Unbans the provided {@link net.dv8tion.jda.entities.User User} from the {@link net.dv8tion.jda.entities.Guild Guild}.
     * This change will be applied immediately.
     *
     * @param user
     *          The {@link net.dv8tion.jda.entities.User User} to unban.
     * @throws net.dv8tion.jda.exceptions.GuildUnavailableException
     *      if the guild is temporarily unavailable
     */
    public void unBan(User user)
    {
        unBan(user.getId());
    }
    
    /**
     * Unbans the {@link net.dv8tion.jda.entities.User User} from the {@link net.dv8tion.jda.entities.Guild Guild} based on the provided userId.
     * This change will be applied immediately.
     *
     * @param userId
     *          The id of the {@link net.dv8tion.jda.entities.User User} to unban.
     * @throws net.dv8tion.jda.exceptions.GuildUnavailableException
     *      if the guild is temporarily unavailable
     */
    public void unBan(String userId)
    {
        if (!guild.isAvailable())
        {
            throw new GuildUnavailableException();
        }
        checkPermission(Permission.BAN_MEMBERS);
        
        ((JDAImpl) guild.getJDA()).getRequester().delete(Requester.DISCORD_API_PREFIX + "guilds/"
                + guild.getId() + "/bans/" + userId);
    }
    
    /**
     * Undeafens a {@link net.dv8tion.jda.entities.User User} in this {@link net.dv8tion.jda.entities.Guild Guild}.
     * Requires the {@link net.dv8tion.jda.Permission#VOICE_DEAF_OTHERS VOICE_DEAF_OTHERS} permission.
     *
     * @param user
     *      The user who should be undeafened.
     * @throws net.dv8tion.jda.exceptions.GuildUnavailableException
     *      if the guild is temporarily unavailable
     * @see JDA_GuildManager#deafen(User)
     */
    public void undeafen(User user)
    {
        this.deafen(user, false);
    }
    
    /**
     * Unmutes a {@link net.dv8tion.jda.entities.User User} in this {@link net.dv8tion.jda.entities.Guild Guild}.
     * Requires the {@link net.dv8tion.jda.Permission#VOICE_MUTE_OTHERS VOICE_MUTE_OTHERS} permission.
     *
     * @param user
     *      The user who should be unmuted.
     * @throws net.dv8tion.jda.exceptions.GuildUnavailableException
     *      if the guild is temporarily unavailable
     * @see JDA_GuildManager#mute(User)
     */
    public void unmute(User user)
    {
        this.mute(user, false);
    }
    
    /**
     * Leaves this {@link net.dv8tion.jda.entities.Guild Guild}.
     * If the logged in {@link net.dv8tion.jda.entities.User User} is the owner of
     * this {@link net.dv8tion.jda.entities.Guild Guild}, this method will throw an {@link net.dv8tion.jda.exceptions.PermissionException PermissionException}.
     * This change will be applied immediately.
     *
     * @throws net.dv8tion.jda.exceptions.GuildUnavailableException
     *      if the guild is temporarily unavailable
     * @throws net.dv8tion.jda.exceptions.PermissionException
     *      if the account JDA is using is the owner of the Guild
     */
    public void leave()
    {
        if (guild.getJDA().getSelfInfo().getId().equals(guild.getOwnerId()))
        {
            throw new PermissionException("You can not leave a guild as the Guild-Owner. Use JDAGuildManager#transferOwnership first, or use JDAGuildManager#delete()");
        }
        if (!guild.isAvailable())
        {
            throw new GuildUnavailableException();
        }
        ((JDAImpl) guild.getJDA()).getRequester().delete(Requester.DISCORD_API_PREFIX + "users/@me/guilds/" + guild.getId());
    }
    
    private void deafen(User user, boolean deafen)
    {
        if (!guild.isAvailable())
        {
            throw new GuildUnavailableException();
        }
        
        checkPermission(Permission.VOICE_DEAF_OTHERS);
        
        String url = Requester.DISCORD_API_PREFIX + "guilds/" + guild.getId() + "/members/" + user.getId();
        
        ((JDAImpl) guild.getJDA()).getRequester()
                .patch(url, new JSONObject().put("deaf", deafen));
    }
    
    private void mute(User user, boolean mute)
    {
        if (!guild.isAvailable())
        {
            throw new GuildUnavailableException();
        }
        
        checkPermission(Permission.VOICE_MUTE_OTHERS);
        
        String url = Requester.DISCORD_API_PREFIX + "guilds/" + guild.getId() + "/members/" + user.getId();
        
        ((JDAImpl) guild.getJDA()).getRequester()
                .patch(url, new JSONObject().put("mute", mute));
    }
    
    private JSONObject getFrame()
    {
        return new JSONObject().put("name", guild.getName());
    }
    
    private void update(JSONObject object)
    {
        ((JDAImpl) guild.getJDA()).getRequester().patch(Requester.DISCORD_API_PREFIX + "guilds/" + guild.getId(), object);
    }
    
    private void checkPermission(Permission perm)
    {
        if (!PermissionUtil.checkPermission(getGuild().getJDA().getSelfInfo(), perm, getGuild()))
            throw new PermissionException(perm);
    }
    
    private void checkPosition(User u)
    {
        if(!PermissionUtil.canInteract(guild.getJDA().getSelfInfo(), u, guild))
            throw new PermissionException("Can't modify a user with higher or equal highest role than yourself!");
    }
    
    private void checkPosition(Role r)
    {
        if(!PermissionUtil.canInteract(guild.getJDA().getSelfInfo(), r))
            throw new PermissionException("Can't modify a user with higher or equal highest role than yourself!");
    }
}
