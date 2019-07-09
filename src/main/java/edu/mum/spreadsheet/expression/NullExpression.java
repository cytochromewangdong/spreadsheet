package edu.mum.spreadsheet.expression;

public class NullExpression implements Expression {
	public final static NullExpression DEFAULT_NULL = new NullExpression();

	private NullExpression() {

	}

	@Override
	public String getValue() {
		return null;
	}

	@Override
	public Number getNumberValue() {
		return 0;
	}
}
