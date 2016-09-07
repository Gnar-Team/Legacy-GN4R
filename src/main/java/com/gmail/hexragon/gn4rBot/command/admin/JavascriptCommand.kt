package com.gmail.hexragon.gn4rBot.command.admin

import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor
import com.gmail.hexragon.gn4rBot.managers.commands.annotations.Command
import com.gmail.hexragon.gn4rBot.managers.users.PermissionLevel
import com.gmail.hexragon.gn4rBot.util.GnarMessage
import org.apache.commons.lang3.StringUtils
import javax.script.ScriptEngineManager
import javax.script.ScriptException

@Command(
        aliases = arrayOf("runjs"),
        description = "Run JavaScript commands.",
        permissionRequired = PermissionLevel.BOT_MASTER,
        showInHelp = false
)
class JavascriptCommand : CommandExecutor()
{
    // EY remember SKRIPT? yeah this function is pretty much the same
    //       fun method_name(input name : input type...)
    override fun execute(message : GnarMessage?, args : Array<out String>?)
    {                           // ^ question mark after means possibly null
        //kotlin doesnt have raw types (explicitly anyways)
        val engine = ScriptEngineManager().getEngineByName("javascript")
        // dont need to tell kotlin that its a script engine, kotlin automatically knows if you assign in same line
        
        engine.put("jda", message?.jda)
        engine.put("message", message)
        engine.put("guild", guildManager) // no need for ; (you can put if you want though)
        engine.put("channel", message?.channel)
        
        
        val script = StringUtils.join(args, " ")
        // access any java library ever
        
        message?.reply("Running `$script`.")
        // string interpolation, no fucking need for messy concats
        
        val result : Any?
        // if you dont assign, then you need to put the type
        // kotlin declarations are basicalyl -> [name : type]
        // Any == Object
        //   it is nullable (see the ?) since javascript engine might not output anything at all
        //   and kotlin wont compile if you try to assign it to a nullable type
        
        try
        {
            result = engine.eval(script)
        }
        catch (e : ScriptException)
        {
            message?.reply("The error `${e.toString()}` occurred while executing the JavaScript statement.")
            // only execute reply if message is not null (thanks! '?' null safety!)
            return
        }
        
        if (result != null)
        {
            if (result.javaClass == Int::class.javaObjectType
                    || result.javaClass == Double::class.javaObjectType
                    || result.javaClass == Float::class.javaObjectType
                    || result.javaClass == String::class.javaObjectType
                    || result.javaClass == Boolean::class.javaObjectType)
            {
                // to get a variable's java class, we do value.javaClass
                // to get a static java class type from a java class, we do Name::class.java (classes AND primitives)
                //         or class.javaObjectType (classes only) or class.javaPrimitiveType (primitives only)
                // if we wanna get a kotlin class we just do Name::class
                
                message?.reply("The result is `$result`.")
                
                //extra note on the nullsafety thing
                // lets say 'maeyrl' exists, but his field 'dick' is null
                // -> val dickOfmae = maeyrl?.dick?.size (using nullsafety)
                // if any of 'maeyrl' or 'dick' happens before the final pointer
                // kotlin will assign it a null (according to the docks
            }
        }
    }
}