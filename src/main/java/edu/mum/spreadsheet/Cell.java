package edu.mum.spreadsheet;

import java.util.ArrayList;
import java.util.List;

import edu.mum.spreadsheet.ex.CircularReferenceException;
import edu.mum.spreadsheet.expression.BridgeExpression;
import edu.mum.spreadsheet.expression.CellExpression;
import edu.mum.spreadsheet.expression.IlegalExpression;
import edu.mum.spreadsheet.expression.NullExpression;
import edu.mum.spreadsheet.expression.NumberValueExpression;
import edu.mum.spreadsheet.expression.StringValueExpression;
import edu.mum.spreadsheet.observer.Event;

public class Cell extends ContainedSubject<Cell> implements ChangeListener<Cell>, CellExpression {

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

	private CellExpression expression = NullExpression.DEFAULT_NULL;

	public CellExpression getExpression() {
		return expression;
	}

	public void setExpressionObj(CellExpression expression) {
		this.setExpressionObj(expression, new ArrayList<>());
	}

	public void setExpressionObj(CellExpression expression, List<Cell> relatedCells) {
		this.getContainer().beforeCellChange(this);
		relatedCell.stream().forEach(c -> c.removeListener(this));
		relatedCell.clear();
		this.expression = expression;
		relatedCell.addAll(relatedCells);
		relatedCell.stream().forEach(c -> c.registerListener(this));
		this.getContainer().afterCellChange(this);
		this.postAll(this);
	}

	public void linkCell(Cell targetCell) {
		this.setExpressionObj(new BridgeExpression(targetCell,
				"link to [" + targetCell.getRow().row + "," + targetCell.column + "]"));
	}

	public String getValue() {
		return getExpression().getValue();
	}

	public Number getNumberValue() {
		return getExpression().getNumberValue();
	}

	public String getFormula() {
		return getExpression().getFormula();
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
		this.setExpressionObj(new StringValueExpression(value));
	}

	public void setValue(Number value) {
		this.setExpressionObj(new NumberValueExpression(value));
	}

	@Override
	public void onChange(Event<Cell> event) {
		this.getExpression().resetEvaluate();
	}

	@Override
	public String toString() {
		return String.format(paddingFormat, getValue());
	}

	public void setExpression(String expression) {
		expression = expression.trim();
		if (expression.startsWith("\"")) {
			if (expression.length() <= 1) {
				// throw new ExpressionInvalidException("Invalid Input");
				// return false;
				this.setExpressionObj(new BridgeExpression(IlegalExpression.DEFAULT_INSTANCE, expression));
				return;
			}
			if (expression.endsWith("\"")) {
				this.setValue(expression.substring(1, expression.length() - 1));
			} else {
				// throw new ExpressionInvalidException("Invalid Input");
				// return false;
				this.setExpressionObj(new BridgeExpression(IlegalExpression.DEFAULT_INSTANCE, expression));
			}
			return;
			// return true;
		}
		Tokenizer.parseExpression(expression, this.getContainer(), this);
	}
}
