package edu.mum.spreadsheet.expression;

public class NullExpression implements CellExpression {
	public final static NullExpression DEFAULT_NULL = new NullExpression();

	private NullExpression() {

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
		return null;
	}

}
