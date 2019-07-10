package edu.mum.spreadsheet.expression.rules;

import java.util.ArrayList;
import java.util.List;

import edu.mum.spreadsheet.ex.ExpressionInvalidException;

public class Tokenizer {
	public enum Token {
		Addition('+'), Division('/'), Multiplication('*'), Subtraction('-');
		private final char symbol;

		public char getSymbol() {
			return symbol;
		}

		private Token(char symbol) {
			this.symbol = symbol;
		}
	}

	private static final String tokenChars;
	static {
		StringBuilder sb = new StringBuilder();
		for (Token t : Token.values()) {
			sb.append(t.getSymbol());
		}
		tokenChars = sb.toString();
	}
	private static String startChars = "[(";
	private static String endChars = "])";

	private static String splitor = tokenChars + startChars + ")";

	private static String allInOne = tokenChars + startChars + endChars;

	public List<Object> parseExpression(String expression) {
		List<Object> result = new ArrayList<>();
		char[] ec = expression.trim().toCharArray();
		StringBuilder collector = new StringBuilder();
		boolean startCollectCell = false;
		for (char c : ec) {
			if (c == 0x20) {
				continue;
			}
			if (splitor.indexOf(c) >= 0) {
				if (collector.length() > 0) {
					Number v = Double.parseDouble(collector.toString());
					collector = new StringBuilder();
				}
				if (c != '[') {
					result.add(c);
				} else {
					startCollectCell = true;
				}
				continue;
			}
			if (c == ']') {
				if (!startCollectCell) {
					throw new ExpressionInvalidException("] does not have start");
				} else {
					String cellExr = collector.toString();
				}
				continue;
			}

			collector.append(c);
		}
		return result;
	}
}
