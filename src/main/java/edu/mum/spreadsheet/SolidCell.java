package edu.mum.spreadsheet;

import edu.mum.spreadsheet.ex.ValueExpression;
import edu.mum.spreadsheet.expression.NumberValueExpression;
import edu.mum.spreadsheet.expression.StringValueExpression;

public class SolidCell extends Cell {

	@Override
	public String getValue() {
		return getExpression().getValue();
	}

	@Override
	public Number getNumberValue() {
		return getExpression().getNumberValue();
	}

	public void setValue(String value) {
		this.setExpression(new StringValueExpression(value));
	}

	public void setValue(Number value) {
		this.setExpression(new NumberValueExpression(value));
	}

	public void setExpression(String text) {
		if (text.startsWith("\"")) {
			if (text.length() <= 1) {
				throw new ValueExpression("Invalid Input");
			}
			if (text.endsWith("\"")) {
				setValue(text.substring(1, text.length() - 1));
			} else {
				throw new ValueExpression("Invalid Input");
			}
			return;
		}
		// check expression
	}
}
