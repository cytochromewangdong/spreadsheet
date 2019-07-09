package edu.mum.spreadsheet.ex;

public class ValueExpression extends RuntimeException {

	public ValueExpression() {
	}

	public ValueExpression(String message, Throwable cause) {
		super(message, cause);
	}

	public ValueExpression(String message) {
		super(message);
	}

	public ValueExpression(Throwable cause) {
		super(cause);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -6967541974167684720L;

}
