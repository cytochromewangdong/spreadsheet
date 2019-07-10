package edu.mum.spreadsheet.expression;

public interface Expression {

	public String getRawString();

	public String getValue();

	public Number getNumberValue();
	
	default public void evaluate() {
		
	}
}
