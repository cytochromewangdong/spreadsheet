package edu.mum.spreadsheet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.mum.spreadsheet.ex.CircularReferenceException;
import edu.mum.spreadsheet.expression.Expression;
import edu.mum.spreadsheet.expression.NullExpression;

public abstract class Cell extends ContainedSubject<Cell> implements Contained, ChangeListener<Cell>, Expression {

	protected final int row;
	protected final int column;
	protected List<Cell> relatedCell = new ArrayList<>();

	public Cell(Sheet container, int row, int column) {
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

			} else {
				throw new CircularReferenceException();
			}
		} finally {
			isEvaluating = false;
		}
	}

	public abstract void setValue(String value);

	public abstract void setValue(Number value);

	public int getEventType() {
		return EventTypeConstant.CELL_VALUE_CHANGE_EVENT;
	}
}
