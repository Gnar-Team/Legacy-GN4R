package com.gmail.hexragon.gn4rBot

import com.gmail.hexragon.gn4rBot.managers.GnarShard
import com.gmail.hexragon.gn4rBot.managers.guild.GuildManager
import com.gmail.hexragon.gn4rBot.util.DiscordBotsInfo
import com.gmail.hexragon.gn4rBot.util.FileManager
import com.gmail.hexragon.gn4rBot.util.PropertiesManager
import com.gmail.hexragon.gn4rBot.util.Utils
import net.dv8tion.jda.JDABuilder
import java.io.File
import java.util.*
import java.util.concurrent.Executors

fun main(args : Array<String>)
{
    val dataFolder = File("_DATA")
    if (!dataFolder.exists())
    {
        println("[ERROR] - Folder '_DATA' not found.")
        System.exit(1)
        return
    }

    GnarBot(GnarBot.authTokens["main-bot"], 4)
}

class GnarBot(val token : String, shardsNum : Int)
{
    companion object Data
    {
        @JvmStatic val shards      = arrayListOf<GnarShard>()
    
        @JvmStatic val startTime   = System.currentTimeMillis()
        @JvmStatic val adminIDs    = FileManager("_DATA/administrators").readLines()
        @JvmStatic val authTokens  = PropertiesManager().load(File("_DATA/tokens.properties"))!!
        @JvmStatic val scheduler   = Executors.newSingleThreadScheduledExecutor()!!
    
        @JvmStatic
        fun getUptimeStamp(compact: Boolean = false) : String
        {
            val seconds = (Date().time - startTime) / 1000
            val minutes = seconds / 60
            val hours = minutes / 60
            val days = hours / 24
            return when
            {
                compact -> "$days days, ${hours % 24} hours, ${minutes % 60} minutes and ${seconds % 60} seconds"
                else    -> "${days}d ${hours % 24}h ${minutes % 60}m ${seconds % 60}s"
            }
        }
    
        @JvmStatic
        fun getGuildCount() : Int
        {
            return shards.map { it.jda.guilds.size }.sum()
        }
    
        @JvmStatic
        fun getGuildManagers() : List<GuildManager>
        {
            return shards.flatMap { it.guildManagers }
        }
    }
    
    init
    {
        var servers = 0
    
        for (id in 0 .. shardsNum - 1)
        {
            val jda = JDABuilder()
                    .useSharding(id, shardsNum)
                    .setBotToken(token)
                    .buildBlocking()
        
            jda.accountManager.setUsername("GNAR")
            jda.accountManager.setGame("_help | _invite")
            jda.accountManager.update()
        
            jda.isAutoReconnect = true
            
            servers += jda.guilds.size
        
            shards.add(GnarShard(jda, id))
        }
    
        DiscordBotsInfo.updateServerCount(servers)
        Utils.startLeagueChampInfo()
    }
}