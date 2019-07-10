package edu.mum.spreadsheet.ex;

public class ExpressionInvalidException extends RuntimeException {

	public ExpressionInvalidException() {
	}

	public ExpressionInvalidException(String message, Throwable cause) {
		super(message, cause);
	}

	public ExpressionInvalidException(String message) {
		super(message);
	}

	public ExpressionInvalidException(Throwable cause) {
		super(cause);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -6967541974167684720L;

}
