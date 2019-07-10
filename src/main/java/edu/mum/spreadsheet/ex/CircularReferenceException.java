package edu.mum.spreadsheet.ex;

public class CircularReferenceException extends ExpressionInvalidException {

	public CircularReferenceException() {
		super();
	}

	public CircularReferenceException(String message, Throwable cause) {
		super(message, cause);
	}

	public CircularReferenceException(String message) {
		super(message);
	}

	public CircularReferenceException(Throwable cause) {
		super(cause);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 4734670937546960938L;

}
