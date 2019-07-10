package edu.mum.spreadsheet.expression;

public class StringValueExpression implements Expression {
	private final String value;

	public StringValueExpression(String value) {
		assert (value != null);
		this.value = value;
		
	}

	@Override
	public String getValue() {
		return this.value;
	}

	@Override
	public Number getNumberValue() {
		try {
			return Double.parseDouble(this.value);
		} catch (Exception e) {
			return 0;
		}
	}

	@Override
	public String getRawString() {
		return this.value;
	}

}
