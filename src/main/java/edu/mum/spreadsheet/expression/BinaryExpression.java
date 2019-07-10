package edu.mum.spreadsheet.expression;

public abstract class BinaryExpression implements Expression {

	protected abstract String getSymbol();

	protected Number cachedValue;

	protected Expression left;
	protected Expression right;

	@Override
	public String getValue() {
		return this.getRawString();
	}

	@Override
	public Number getNumberValue() {
		if (this.cachedValue == null) {
			this.evaluate();
		}
		return cachedValue;
	}

	@Override
	public String getRawString() {
		return left.getRawString() + this.getSymbol() + right.getRawString();
	}

	@Override
	public void evaluate() {
		this.left.evaluate();
		this.right.evaluate();
		this.cachedValue = this.binaryEvaluate(this.left.getNumberValue().doubleValue(),
				this.right.getNumberValue().doubleValue());
	}

	protected abstract Number binaryEvaluate(double left, double right);

}
