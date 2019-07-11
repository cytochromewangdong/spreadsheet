package edu.mum.spreadsheet.expression;

import java.util.function.Supplier;

public enum SymbolToken {
	Addition('+', AdditionExpression::new, 0), Subtraction('-', SubtractionExpression::new, 0), Division('/',
			DivisionExpression::new, 10), Multiplication('*', MultiplicationExpression::new, 10);
	private final char symbol;
	private final Supplier<BinaryExpression> supplier;
	private final int priority;

	public boolean hasHigherPriority(SymbolToken o) {
		return priority - o.priority > 0;
	}

	public char getSymbol() {
		return symbol;
	}

	private SymbolToken(char symbol, Supplier<BinaryExpression> supplier, int priority) {
		this.symbol = symbol;
		this.supplier = supplier;
		this.priority = priority;
	}

	public BinaryExpression create(Expression left, Expression right) {
		BinaryExpression expression = supplier.get();
		expression.left = left;
		expression.right = right;
		return expression;
	}

	@Override
	public String toString() {
		return String.valueOf(this.symbol);
	}
}
