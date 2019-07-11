package edu.mum.spreadsheet;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
		Cell currentCell = this.getCell(row, column);
		currentCell.setExpression(expression);
	}

	public void linkCell(int srcRow, int srcColumn, int toRow, int toColumn) {
		this.getCell(srcRow, srcColumn).linkCell(this.getCell(toRow, toColumn));
	}

	public void beforeCellChange(Cell cell) {

	}

	public void afterCellChange(Cell cell) {

	}

	@Override
	public String toString() {
		int max = data.keySet().stream().max(Integer::compareTo).orElseGet(() -> 0);
		return IntStream.range(1, max + 1).mapToObj(idx -> getRow(idx).toString()).collect(Collectors.joining("\n"));
	}
}
