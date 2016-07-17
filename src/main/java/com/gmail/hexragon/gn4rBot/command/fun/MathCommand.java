package com.gmail.hexragon.gn4rBot.command.fun;

import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandManager;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import org.apache.commons.lang3.StringUtils;

import java.text.DecimalFormat;

public class MathCommand extends CommandExecutor
{
	private final DecimalFormat formatter = new DecimalFormat();
	{
		formatter.setDecimalSeparatorAlwaysShown(false);
	}

	public MathCommand(CommandManager manager)
	{
		super(manager);
		setDescription("Calculate fancy math stuff.");
		setUsage("math (expression)");
	}

	@Override
	public void execute(MessageReceivedEvent event, String[] args)
	{
		String exp = StringUtils.join(args, " ");
		double result = new MathConsumer(exp).parse();
		event.getChannel().sendMessage(String.format("%s ➤ Expression `%s` evaluating.", event.getAuthor().getAsMention(), exp));
		event.getChannel().sendMessage(String.format("%s ➤ Final answer: `%s`", event.getAuthor().getAsMention(), formatter.format(result)));
	}

	private class MathConsumer
	{
		private String exp;
		int pos = -1;
		int ch;

		MathConsumer(String exp)
		{
			this.exp = exp;
		}

		void nextChar()
		{
			ch = (++pos < exp.length()) ? exp.charAt(pos) : -1;
		}

		boolean eat(int charToEat)
		{
			while (ch == ' ') nextChar();
			if (ch == charToEat)
			{
				nextChar();
				return true;
			}
			return false;
		}

		double parse()
		{
			nextChar();
			double x = parseExpression();
			if (pos < exp.length()) throw new RuntimeException("Unexpected: " + (char) ch);
			return x;
		}

		// Grammar:
		// expression = term | expression `+` term | expression `-` term
		// term = factor | term `*` factor | term `/` factor
		// factor = `+` factor | `-` factor | `(` expression `)`
		//        | number | functionName factor | factor `^` factor

		double parseExpression()
		{
			double x = parseTerm();
			for (; ; )
			{
				if (eat('+')) x += parseTerm(); // addition
				else if (eat('-')) x -= parseTerm(); // subtraction
				else return x;
			}
		}

		double parseTerm()
		{
			double x = parseFactor();
			for (; ; )
			{
				if (eat('*')) x *= parseFactor(); // multiplication
				else if (eat('/')) x /= parseFactor(); // division
				else return x;
			}
		}

		double parseFactor()
		{
			if (eat('+')) return parseFactor(); // unary plus
			if (eat('-')) return -parseFactor(); // unary minus

			double x;
			int startPos = this.pos;
			if (eat('('))
			{ // parentheses
				x = parseExpression();
				eat(')');
			}
			else if ((ch >= '0' && ch <= '9') || ch == '.')
			{ // numbers
				while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
				x = Double.parseDouble(exp.substring(startPos, this.pos));
			}
			else if (ch >= 'a' && ch <= 'z')
			{ // functions
				while (ch >= 'a' && ch <= 'z') nextChar();
				String func = exp.substring(startPos, this.pos);
				x = parseFactor();
				switch (func)
				{
					case "sqrt":
						x = Math.sqrt(x);
						break;
					case "sin":
						x = Math.sin(x);
						break;
					case "cos":
						x = Math.cos(x);
						break;
					case "tan":
						x = Math.tan(x);
						break;
					case "log":
						x = Math.log10(x);
						break;
					case "ln":
						x = Math.log(x);
						break;
					default:
						throw new RuntimeException("Unknown function: " + func);
				}
			}
			else
			{
				throw new RuntimeException("Unexpected: " + (char) ch);
			}

			if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

			return x;
		}
	}
}
