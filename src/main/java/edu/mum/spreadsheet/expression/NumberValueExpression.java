package edu.mum.spreadsheet.expression;

public class NumberValueExpression implements Expression {
	private Number value;
	private String rawString;
	public NumberValueExpression(Number value) {
		this(value, value.toString());
	}
	
	public NumberValueExpression(Number value, String rawString) {
		assert (value != null);
		this.value = value;
		this.rawString = rawString;
	}

	@Override
	public String getValue() {
		return String.valueOf(value);
	}

	@Override
	public Number getNumberValue() {
		return value;
	}

	@Override
	public String getRawString() {
		return rawString;
	}

}
