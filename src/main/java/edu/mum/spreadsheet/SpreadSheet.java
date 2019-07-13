package edu.mum.spreadsheet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import edu.mum.spreadsheet.observer.Subject;

public class SpreadSheet extends Subject<SpreadSheet>{
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
	public String getValue(int row, int column) {
		if (!data.containsKey(row)) {
			return "";
		}
		return data.get(row).getValue(column);
	}
	public String getFormula(int row, int column) {
		if (!data.containsKey(row)) {
			return "";
		}
		return data.get(row).getFormula(column);
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

	private List<Cell> changedCell = new ArrayList<>();

	public List<Cell> getChangedCellList(){
		return this.changedCell;
	}
	public void beforeCellChange(Cell cell) {
		changedCell.clear();
	}

	public void notifyChanged(Cell c) {
		changedCell.add(c);
	}

	public void afterCellNotified(Cell cell) {
		changedCell.add(cell);
		this.postAll(this);
	}

	@Override
	public String toString() {
		int max = data.keySet().stream().max(Integer::compareTo).orElseGet(() -> 0);
		StringBuilder sb = new StringBuilder();
		sb.append(
				IntStream.range(1, max + 1).mapToObj(idx -> getRow(idx).toString()).collect(Collectors.joining("\n")));
		sb.append("\n");
		sb.append(IntStream.range(1, max + 1).mapToObj(idx -> getRow(idx).toFormulaString())
				.collect(Collectors.joining("\n")));
		sb.append("\n");
		return sb.toString();
	}

	@Override
	public int getEventType() {
		return 0;
	}
}
