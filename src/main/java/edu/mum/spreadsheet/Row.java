package edu.mum.spreadsheet;

import java.util.HashMap;
import java.util.Map;

public class Row extends ContainedSubject<Row> {
	protected final int row;
	private Map<Integer, Cell> data = new HashMap<>();

	public Row(SpreadSheet container, int row) {
		super(container);
		this.row = row;
	}

	public int getEventType() {
		return EventTypeConstant.ROW_CHANGE_EVENT;
	}

	public Cell getCell(int column) {
		if (!data.containsKey(column)) {
			data.put(column, new Cell(this.getContainer(), this.row, column));
		}
		return data.get(column);
	}
}
