package edu.mum.spreadsheet.expression;

import edu.mum.spreadsheet.ex.ExpressionInvalidException;

public class IlegalExpression implements Expression {

	public static final IlegalExpression DEFAULT_INSTANCE = new IlegalExpression();

	private IlegalExpression() {

	}

	@Override
	public String getValue() {
		return null;
	}

	@Override
	public Number getNumberValue() {
		return null;
	}

	public void evaluate() {
		throw new ExpressionInvalidException("Illegal expression.");
	}

}
