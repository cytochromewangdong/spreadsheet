package edu.mum.spreadsheet.expression;

import edu.mum.spreadsheet.ex.ExpressionInvalidException;

public class BridgeExpression implements CellExpression {
	protected Number cachedValue;
	protected String lastException = null;

	protected boolean isEvaluating;
	protected String rawString;
	protected Expression childExpression;
	

	public BridgeExpression(Expression childExpression, String expression) {
		this.childExpression = childExpression;
		this.rawString = expression;
	}

	public String getFormula() {
		return rawString;
	}

	@Override
	public String getValue() {
		if (cachedValue == null) {
			try {
				evaluate();
			} catch (ExpressionInvalidException ex) {
//				ex.printStackTrace();
			}
		}
		return cachedValue == null ? "Error:" + this.getFormula() + ":[" + lastException + "]"
				: this.cachedValue.toString();
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
		if (!isEvaluating) {
			isEvaluating = true;
			try {

				try {
					this.childExpression.evaluate();

					if (this.childExpression.getNumberValue() == null) {
						this.lastException = "invalid expression";
						return;
					}
					this.cachedValue = this.childExpression.getNumberValue();
				} catch (Exception e) {
					this.lastException = e.getMessage();
					throw e;
				}
			} finally {
				isEvaluating = false;
			}
		} else {
			this.lastException = "circular reference";
		}
	}

}
