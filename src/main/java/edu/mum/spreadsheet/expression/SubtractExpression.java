package edu.mum.spreadsheet.expression;

public class SubtractExpression extends BinaryExpression {

	@Override
	protected String getSymbol() {
		return "-";
	}

	@Override
	protected Number binaryEvaluate(double left, double right) {
		return left - right;
	}

}
