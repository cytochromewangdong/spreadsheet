package edu.mum.spreadsheet.expression;

public class NumberValueExpression implements Expression {
	private Number value;

	public NumberValueExpression(Number value) {
		assert (value != null);
		this.value = value;
	}

	@Override
	public String getValue() {
		return String.valueOf(value);
	}

	@Override
	public Number getNumberValue() {
		return value;
	}

}
