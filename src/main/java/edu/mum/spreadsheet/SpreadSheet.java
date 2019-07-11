package edu.mum.spreadsheet;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import edu.mum.spreadsheet.ex.ExpressionInvalidException;

public class SpreadSheet {
	private Map<Integer, Row> data = new HashMap<>();

	public Column getColumn(int Column) {
		return new Column(this, Column);
	}

	public Row getRow(int row) {
		if (!data.containsKey(row)) {
			data.put(row, new Row(this, row));
		}
		return data.get(row);
	}

	public Cell getCell(int row, int column) {
		return this.getRow(row).getCell(column);
	}

	public void setCellValue(int row, int column, Number val) {
		this.getCell(row, column).setValue(val);
	}

	public void setCellValue(int row, int column, String val) {
		this.getCell(row, column).setValue(val);
	}

	public void setExpression(int row, int column, String expression) {
		expression = expression.trim();
		Cell currentCell = this.getCell(row, column);
		if (expression.startsWith("\"")) {
			if (expression.length() <= 1) {
				throw new ExpressionInvalidException("Invalid Input");
			}
			if (expression.endsWith("\"")) {
				currentCell.setValue(expression.substring(1, expression.length() - 1));
			} else {
				throw new ExpressionInvalidException("Invalid Input");
			}
			return;
		}
		// TODO
		// currentCell.setExpression(expression);
		Tokenizer.parseExpression(expression, this, currentCell);
	}

	public void beforeCellChange(Cell cell) {

	}

	public void afterCellChange(Cell cell) {

	}

	@Override
	public String toString() {
		int max = data.keySet().stream().max(Integer::compareTo).orElseGet(() -> 0);
		return IntStream.range(1, max + 1)
				.mapToObj(idx -> getRow(idx).toString())
				.collect(Collectors.joining("\n"));
	}
}
