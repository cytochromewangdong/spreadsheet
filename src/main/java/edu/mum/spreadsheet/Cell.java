package edu.mum.spreadsheet;

import edu.mum.spreadsheet.expression.Expression;
import edu.mum.spreadsheet.expression.NullExpression;
import edu.mum.spreadsheet.observer.Subject;

public abstract class Cell extends Subject<Cell> {

	private Expression expression = NullExpression.DEFAULT_NULL;

	public Expression getExpression() {
		return expression;
	}

	public void setExpression(Expression expression) {
		this.expression = expression;
		this.postAll(this);
	}

	public abstract String getValue();

	public abstract Number getNumberValue();

	public abstract void setValue(String value);

	public abstract void setValue(Number value);

	public abstract void setExpression(String text);

	public int getEventType() {
		return 100;
	}
}
