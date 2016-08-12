package com.gmail.hexragon.gn4rBot.command.admin

import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor
import com.gmail.hexragon.gn4rBot.managers.commands.annotations.Command
import com.gmail.hexragon.gn4rBot.managers.users.PermissionLevel
import com.gmail.hexragon.gn4rBot.util.GnarMessage
import kotlinx.html.a
import kotlinx.html.li
import kotlinx.html.span
import kotlinx.html.stream.appendHTML
import kotlinx.html.ul
import java.io.File
import java.util.LinkedHashMap

@Command(aliases = arrayOf("htmlOfCommands"), permissionRequired = PermissionLevel.BOT_MASTER, showInHelp = false)
class ProcessHTML : CommandExecutor()
{
    override fun execute(message : GnarMessage?, args : Array<out String>?)
    {
        val shownCommands = commandManager.uniqueCommandRegistry.filter { it.value.isShownInHelp }
//
//		val halfpoint = shownCommands.size / 2
//		var count = 0
//		var bool = false
//
//		println("halfpoint = $halfpoint")
//
//		println("\nLIST 1___________________________\n")
//		println("<ul class=\"list-group black-text\">")
//
//		for ((label, cmd) in shownCommands)
//		{
//			if (!bool && count >= halfpoint)
//			{
//				println("</ul>")
//				println("\nLIST 2___________________________\n")
//				println("<ul class=\"list-group black-text\">")
//				bool = true
//			}
//			else
//			{
//				count++
//			}
//			if (cmd.isShownInHelp) println("\t<li class=\"list-group-item\"><span class=\"badge\"> ${cmd.description}</span> ${commandManager.token}$label</li>")
//
//		}
//		println("</ul>")
        
        val halfpoint = shownCommands.size / 2
        var count = 0
        
        val list1 = LinkedHashMap<String, CommandExecutor>()
        val list2 = LinkedHashMap<String, CommandExecutor>()
        for ((label, cmd) in shownCommands)
        {
            if (count >= halfpoint) list2.put(label, cmd)
            else list1.put(label, cmd)
            count++
        }

//		val html = createHTML(true).html {
//
//			head {
//				meta {charset = "utf-8"}
//				meta(content = "IE=edge") {httpEquiv = "X-UA-Compatible"}
//				meta(name = "viewport", content = "width=device-width, initial-scale=1")
//				meta(name = "description", content = "Documentation for GNAR bot.")
//				meta(name = "author", content = "Avalon and Maeyrl")
//
//				title("GNAR - Discord Bot")
//
//				link(href="vendor/bootstrap/css/bootstrap.min.css", rel="stylesheet")
//				link(href="css/freelancer.css", rel="stylesheet")
//				link(href="vendor/font-awesome/css/font-awesome.min.css", rel="stylesheet", type="text/css")
//				link(href="https://fonts.googleapis.com/css?family=Montserrat:400,700", rel="stylesheet", type="text/css")
//				link(href="https://fonts.googleapis.com/css?family=Lato:400,700,400italic,700italic", rel="stylesheet", type="text/css")
//			}
//
//			body() {
//				ul(classes = "list-group black-text")
//				{
//					list1.forEach() { label, cmd ->
//						li {
//							a(classes = "list-group-item") {
//								span(classes = "badge") { +cmd.description }
//								+label
//							}
//						}
//					}
//				}
//				button(type = ButtonType.button) { attributes["wow"] = "true" }
//			}
//		}
        
        val builder = StringBuilder()
        
        builder.appendHTML(true).ul(classes = "list-group black-text") {
            list1.forEach() { label, cmd ->
                li {
                    a(classes = "list-group-item") {
                        +label
                        span(classes = "badge") { +cmd.description }
                    }
                }
            }
        }
        
        builder.appendHTML(true).ul(classes = "list-group black-text") {
            list2.forEach() { label, cmd ->
                li {
                    a(classes = "list-group-item") {
                        +label
                        span(classes = "badge") { +cmd.description }
                    }
                }
            }
        }
        
        val file = File("_DATA/generatedHtml.html")
        file.writeText(builder.toString())
        
        message?.reply("Created HTML file of commands at ${file.path}")
    }
}