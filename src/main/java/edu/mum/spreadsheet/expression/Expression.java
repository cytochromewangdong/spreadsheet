package edu.mum.spreadsheet.expression;

public interface Expression {

	public String getValue();

	public Number getNumberValue();
	
	default public void evaluate() {
		
	}
	default public void resetEvaluate() {
		
	}
}
