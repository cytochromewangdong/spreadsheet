package edu.mum.spreadsheet.expression;

public interface CellExpression extends Expression {
	String getFormula();
	default boolean hasError() {
		return false;
	}
}
