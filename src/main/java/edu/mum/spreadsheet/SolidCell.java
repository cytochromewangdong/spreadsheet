package edu.mum.spreadsheet;

import edu.mum.spreadsheet.expression.NumberValueExpression;
import edu.mum.spreadsheet.expression.StringValueExpression;
import edu.mum.spreadsheet.observer.Event;

public class SolidCell extends Cell {


	public SolidCell(Sheet container, int row, int column) {
		super(container, row, column);
	}

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

	@Override
	public void onChange(Event<Cell> event) {
		this.getExpression().evaluate();
	}

}
