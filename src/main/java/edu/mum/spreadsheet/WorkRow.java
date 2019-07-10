package edu.mum.spreadsheet;

import java.util.HashMap;
import java.util.Map;

public class WorkRow extends Row {

	public WorkRow(WorkSheet container, int row) {
		super(container, row);
	}

	private Map<Integer, Cell> data = new HashMap<>();

	public Cell getCell(int column) {
		if (!data.containsKey(column)) {
			data.put(column, new WorkCell(this.getContainer(), this.row, column));
		}
		return data.get(column);
	}

}
