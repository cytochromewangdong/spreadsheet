package edu.mum.spreadsheet.expression;

public class DivisionExpression extends BinaryExpression {

	@Override
	protected SymbolToken getSymbol() {
		return SymbolToken.Division;
	}

	@Override
	protected Number binaryEvaluate(double left, double right) {
		return left / right;
	}

}
