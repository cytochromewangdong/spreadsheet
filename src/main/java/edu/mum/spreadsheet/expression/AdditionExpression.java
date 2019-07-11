package edu.mum.spreadsheet.expression;

public class AdditionExpression extends BinaryExpression {

	@Override
	protected SymbolToken getSymbol() {
		return SymbolToken.Addition;
	}

	@Override
	protected Number binaryEvaluate(double left, double right) {
		return left + right;
	}

}
