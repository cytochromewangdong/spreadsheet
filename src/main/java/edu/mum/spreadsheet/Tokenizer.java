package edu.mum.spreadsheet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

import edu.mum.spreadsheet.ex.ExpressionInvalidException;
import edu.mum.spreadsheet.expression.BridgeExpression;
import edu.mum.spreadsheet.expression.Expression;
import edu.mum.spreadsheet.expression.IlegalExpression;
import edu.mum.spreadsheet.expression.NumberValueExpression;
import edu.mum.spreadsheet.expression.SymbolToken;

public class Tokenizer {

	private static final String tokenChars;
	private static final Map<Character, SymbolToken> tokenMapper = new HashMap<>();
	static {
		StringBuilder sb = new StringBuilder();
		for (SymbolToken t : SymbolToken.values()) {
			sb.append(t.getSymbol());
			tokenMapper.put(t.getSymbol(), t);
		}
		tokenChars = sb.toString();
	}
	private static String startChars = "[(";
	// private static String endChars = "])";

	private static String splitor = tokenChars + startChars + ")";

	// private static String allInOne = tokenChars + startChars + endChars;
	private static Expression parseExpression(List<Object> list) {
		Stack<Object> eleStack = new Stack<>();
		Stack<Object> symbolStack = new Stack<>();
		Character leftParenthesis = '(';
		Character rightParenthesis = ')';
		for (Object o : list) {
			if (o instanceof SymbolToken) {
				SymbolToken currToken = (SymbolToken) o;
				if (!symbolStack.isEmpty()) {

					Object obj = symbolStack.peek();
					if (obj instanceof SymbolToken) {

						SymbolToken topOldToken = (SymbolToken) obj;
						if (!currToken.hasHigherPriority(topOldToken)) {
							// Remove top
							symbolStack.pop();
							Expression right = (Expression) eleStack.pop();
							Expression left = (Expression) eleStack.pop();
							eleStack.push(topOldToken.create(left, right));
						}
					}
				}
				symbolStack.push(o);
				continue;
			}
			if (o instanceof Character) {
				Character c = (Character) o;
				if (c.equals(rightParenthesis)) {
					SymbolToken token = (SymbolToken) symbolStack.pop();
					//
					if (!leftParenthesis.equals(symbolStack.pop())) {
						throw new ExpressionInvalidException("Invalid exception!");
					}
					Expression right = (Expression) eleStack.pop();
					Expression left = (Expression) eleStack.pop();
					if (!leftParenthesis.equals(eleStack.pop())) {
						throw new ExpressionInvalidException("Invalid exception!");
					}
					eleStack.push(token.create(left, right));
					continue;
				} else {
					// leftParenthesis
					// eleStack.push(o);
					symbolStack.push(o);
					// continue as symbol
				}
			}
			eleStack.push(o);
		}
		while (!symbolStack.isEmpty()) {
			SymbolToken token = (SymbolToken) symbolStack.pop();
			Expression right = (Expression) eleStack.pop();
			Expression left = (Expression) eleStack.pop();
			eleStack.push(token.create(left, right));
		}
		if (eleStack.size() != 1) {
			throw new ExpressionInvalidException("Invalid exception!");
		}
		return (Expression) eleStack.pop();
	}

	private static List<Object> parseExpression(String expression, SpreadSheet sheet) {
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
					try {
						Number v = Double.parseDouble(collector.toString());
						result.add(new NumberValueExpression(v));
						collector = new StringBuilder();
					} catch (Exception ex) {
						throw new ExpressionInvalidException(collector.toString() + " is not a valid number", ex);
					}
				}
				if (c != '[') {
					if (tokenMapper.containsKey(c)) {
						result.add(tokenMapper.get(c));
					} else {
						result.add(c);
					}
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
					collector = new StringBuilder();
					result.add(parseCellExr(cellExr, sheet));
				}
				continue;
			}

			collector.append(c);
		}
		if (collector.length() > 0) {
			Number v = Double.parseDouble(collector.toString());
			result.add(new NumberValueExpression(v));
		}
		return result;
	}

	private static Cell parseCellExr(String cellExr, SpreadSheet sheet) {
		int pos = cellExr.indexOf(",");
		if (pos <= 0) {
			throw new ExpressionInvalidException("Cell expression is not correct:[" + cellExr + "]");
		}
		try {
			int row = Integer.parseInt(cellExr.substring(0, pos).trim());
			int column = Integer.parseInt(cellExr.substring(pos + 1).trim());
			return sheet.getCell(row, column);
		} catch (Exception ex) {
			throw new ExpressionInvalidException("Cell expression is not correct:[" + cellExr + "]", ex);
		}
	}

	public static void parseExpression(String expression, SpreadSheet sheet, Cell cell) {

		try {
			List<Object> list = parseExpression(expression, sheet);
			if (list.size() == 1 && list.get(0) instanceof NumberValueExpression) {
				cell.setExpressionObj((NumberValueExpression) list.get(0));
			} else {
				List<Cell> related = list.stream().filter(e -> (e instanceof Cell)).map(e -> (Cell) e)
						.collect(Collectors.toList());
				Expression expressionObj = parseExpression(list);

				cell.setExpressionObj(new BridgeExpression(expressionObj, expression), related);
				// cell.evaluate();
			}
		} catch (Exception e) {
			cell.setExpressionObj(new BridgeExpression(new IlegalExpression(), expression));
		}
		// return true;
	}

	public static void main(String[] args) {
		SpreadSheet sheet = new SpreadSheet();
		sheet.setCellValue(1, 2, 10.0);
		sheet.setCellValue(2, 2, 66.0);
		sheet.setCellValue(7, 2, 88);
		sheet.setExpression(4, 2, "[7,2]");
		System.out.println(sheet);
		System.out.println("---------------");
		// sheet.setExpression(3, 2, "[4,2]");
		sheet.setExpression(7, 2, "[1,2]+[2,2]+[3,2]+[4,2]+[5,2]+199");

		// parseExpression(Tokenizer.parseExpression("[1,2]+([2,2]+[3,2]+[4,2 ]+[5,2])+
		// 99.9087", sheet));
		System.out.println(sheet);
		System.out.println("---------------");
		// sheet.setExpression(4, 2, "[7,2]");
		sheet.setCellValue(4, 2, 1);
		sheet.setExpression(8, 2, "[1,2]asdfoiuoi()))+[2,2]+[3,2]+[4,2]+[5,2]+199");
//		sheet.setExpression(9, 2, "[9,2]");
		sheet.linkCell(9, 2, 8, 2);
		System.out.println(sheet);
	}
}
