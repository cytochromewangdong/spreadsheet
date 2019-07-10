package edu.mum.spreadsheet;

import edu.mum.spreadsheet.expression.NumberValueExpression;
import edu.mum.spreadsheet.expression.StringValueExpression;
import edu.mum.spreadsheet.observer.Event;

public class WorkCell extends Cell {


	public WorkCell(Sheet container, int row, int column) {
		super(container, row, column);
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
