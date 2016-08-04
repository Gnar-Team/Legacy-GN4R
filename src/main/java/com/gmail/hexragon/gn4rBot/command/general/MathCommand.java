package com.gmail.hexragon.gn4rBot.command.general;

import com.gmail.hexragon.gn4rBot.managers.commands.Command;
import com.gmail.hexragon.gn4rBot.managers.commands.CommandExecutor;
import net.dv8tion.jda.entities.Message;
import org.apache.commons.lang3.StringUtils;

import java.text.DecimalFormat;

@Command(
		aliases = "math",
		usage = "(expression)",
		description = "Calculate fancy math expressions."
)
public class MathCommand extends CommandExecutor
{
	private final DecimalFormat formatter = new DecimalFormat() {{setDecimalSeparatorAlwaysShown(false);}};
	
	@Override
	public void execute(Message message, String[] args)
	{
		if (args.length == 0)
		{
			message.getChannel().sendMessage(String.format("%s ➜ Please provide a math expression.", message.getAuthor().getAsMention()));
			return;
		}
		
		String exp = StringUtils.join(args, " ");
		double result = new Expression(exp).eval();
		message.getChannel().sendMessage(String.format("%s ➜ Expression `%s` evaluating.", message.getAuthor().getAsMention(), exp));
		message.getChannel().sendMessage(String.format("%s ➜ Final answer: `%s`", message.getAuthor().getAsMention(), formatter.format(result)));
	}
	
	private class Expression
	{
		private final String exp;
		int pos = -1;
		int ch;
		
		Expression(String exp)
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
		
		double eval()
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
					case "asin":
						x = Math.asin(x);
						break;
					case "acos":
						x = Math.acos(x);
						break;
					case "atan":
						x = Math.atan(x);
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
