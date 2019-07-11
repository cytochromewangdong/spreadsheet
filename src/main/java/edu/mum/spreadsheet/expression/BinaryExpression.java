package edu.mum.spreadsheet.expression;

import edu.mum.spreadsheet.ex.ExpressionInvalidException;

public abstract class BinaryExpression implements Expression {

	protected Number cachedValue;

	protected abstract SymbolToken getSymbol();

	protected Expression left;
	protected Expression right;

	@Override
	public String getValue() {
		if (cachedValue == null) {
			try {
				evaluate();
			} catch (ExpressionInvalidException ex) {
				ex.printStackTrace();
			}
		}
		return cachedValue == null ? "Error:" : this.cachedValue.toString();
	}

	@Override
	public Number getNumberValue() {
		if (this.cachedValue == null) {
			this.evaluate();
		}
		return cachedValue;
	}

	public void resetEvaluate() {
		cachedValue = null;
	}

	@Override
	public void evaluate() {

		this.cachedValue = null;
		this.left.evaluate();
		this.right.evaluate();
		if (this.left.getNumberValue() == null || this.right.getNumberValue() == null) {
			throw new ExpressionInvalidException("Invalid expression or circular reference!");
		}
		this.cachedValue = this.binaryEvaluate(this.left.getNumberValue().doubleValue(),
				this.right.getNumberValue().doubleValue());

	}

	protected abstract Number binaryEvaluate(double left, double right);

}
