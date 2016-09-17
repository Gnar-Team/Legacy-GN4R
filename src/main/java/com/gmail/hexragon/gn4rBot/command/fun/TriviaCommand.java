package com.gmail.hexragon.gn4rBot.command.fun;

import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.managers.commands.annotations.Command;
import com.gmail.hexragon.gn4rBot.util.GnarMessage;
import com.gmail.hexragon.gn4rBot.util.TriviaQuestions;
import org.apache.commons.lang3.StringUtils;

@Command(
        aliases = {"trivia"},
        description = "Random Trivia Questions, courtesy of Twentysix",
        showInHelp = true
)
public class TriviaCommand extends CommandExecutor {
    @Override
    public void execute(GnarMessage message, String[] args) {

        if(!TriviaQuestions.isSetup()) {
           TriviaQuestions.init();
        }

        if(args.length > 0) {
            message.reply(TriviaQuestions.getRandomQuestion(StringUtils.join(args, " ")));
        } else {
            message.reply(TriviaQuestions.getRandomQuestion());
        }

    }
}
