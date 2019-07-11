package edu.mum.spreadsheet.expression;

public abstract class BinaryExpression implements Expression {

	protected abstract SymbolToken getSymbol();

	protected Number cachedValue;
	protected String lastException = null;

	protected Expression left;
	protected Expression right;

	@Override
	public String getValue() {
		return cachedValue == null ? "Error:" + this.getRawString() + "[" + lastException + "]"
				: this.cachedValue.toString();
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
		this.cachedValue = null;
		this.lastException = null;
		try {
			this.left.evaluate();
			this.right.evaluate();
			this.cachedValue = this.binaryEvaluate(this.left.getNumberValue().doubleValue(),
					this.right.getNumberValue().doubleValue());
		} catch (Exception e) {
			this.lastException = e.getMessage();
			throw e;
		}
	}

	protected abstract Number binaryEvaluate(double left, double right);

}
