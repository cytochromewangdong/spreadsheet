package edu.mum.spreadsheet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.mum.spreadsheet.ex.CircularReferenceException;
import edu.mum.spreadsheet.expression.Expression;
import edu.mum.spreadsheet.expression.NullExpression;
import edu.mum.spreadsheet.expression.NumberValueExpression;
import edu.mum.spreadsheet.expression.StringValueExpression;
import edu.mum.spreadsheet.observer.Event;

public class Cell extends ContainedSubject<Cell> implements Contained, ChangeListener<Cell>, Expression {

	private static final String paddingFormat = "%-15s";
	protected final int row;
	protected final int column;
	protected List<Cell> relatedCell = new ArrayList<>();

	public Cell(SpreadSheet container, int row, int column) {
		super(container);
		this.row = row;
		this.column = column;
	}

	public Row getRow() {
		return this.getContainer().getRow(this.row);
	}

	private Expression expression = NullExpression.DEFAULT_NULL;

	public Expression getExpression() {
		return expression;
	}

	public void setExpression(Expression expression, Cell... relatedCells) {
		this.getContainer().beforeCellChange(this);
		relatedCell.stream().forEach(c -> c.removeListener(this));
		relatedCell.clear();
		this.expression = expression;
		relatedCell.addAll(Arrays.asList(relatedCells));
		relatedCell.stream().forEach(c -> c.registerListener(this));
		this.getContainer().afterCellChange(this);
		this.postAll(this);
	}

	public String getValue() {
		return getExpression().getValue();
	}

	public Number getNumberValue() {
		return getExpression().getNumberValue();
	}

	public String getRawString() {
		return getExpression().getRawString();
	}

	private boolean isEvaluating;

	public void evaluate() {
		try {
			if (!isEvaluating) {
				isEvaluating = true;
				this.expression.evaluate();
			} else {
				throw new CircularReferenceException();
			}
		} finally {
			isEvaluating = false;
		}
	}

	public int getEventType() {
		return EventTypeConstant.CELL_VALUE_CHANGE_EVENT;
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

	@Override
	public String toString() {
		return String.format(paddingFormat, getValue());
	}
}
