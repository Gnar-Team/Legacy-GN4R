package com.gmail.hexragon.gn4rBot.command.fun;

import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.managers.commands.annotations.Command;
import com.gmail.hexragon.gn4rBot.util.GnarMessage;
import com.gmail.hexragon.gn4rBot.util.TriviaQuestions;

@Command(
        aliases = {"answer"},
        description = "Random Trivia Questions, courtesy of Twentysix",
        showInHelp = true
)
public class TriviaAnswerCommand extends CommandExecutor {
    @Override
    public void execute(GnarMessage message, String[] args) {

        if(!TriviaQuestions.isSetup()) {
            TriviaQuestions.init();
        }

        try {
            int key = Integer.valueOf(args[0]);
            message.reply(TriviaQuestions.getAnswer(key));
        } catch (Exception e) {
            message.reply("Please enter a number.");
        }

    }
}
