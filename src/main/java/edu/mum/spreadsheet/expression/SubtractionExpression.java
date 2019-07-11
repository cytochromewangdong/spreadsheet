package edu.mum.spreadsheet.expression;

public class SubtractionExpression extends BinaryExpression {

	@Override
	protected SymbolToken getSymbol() {
		return SymbolToken.Subtraction;
	}

	@Override
	protected Number binaryEvaluate(double left, double right) {
		return left - right;
	}

}
