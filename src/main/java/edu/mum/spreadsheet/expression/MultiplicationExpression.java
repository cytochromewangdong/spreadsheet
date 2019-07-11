package edu.mum.spreadsheet.expression;

public class MultiplicationExpression extends BinaryExpression {

	@Override
	protected SymbolToken getSymbol() {
		return SymbolToken.Multiplication;
	}

	@Override
	protected Number binaryEvaluate(double left, double right) {
		return left * right;
	}

}
