package edu.mum.spreadsheet.expression;

public class EmptyExpression implements CellExpression {
	public final static EmptyExpression DEFAULT_EMPTY = new EmptyExpression();

	private EmptyExpression() {

	}

	@Override
	public String getValue() {
		return "";
	}

	@Override
	public Number getNumberValue() {
		return 0;
	}

	@Override
	public String getFormula() {
		return "";
	}

}
