package edu.mum.spreadsheet;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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

	@Override
	public String toString() {
		var summary = data.keySet().stream().collect(Collectors.summarizingInt(x -> x));
		int min = summary.getMin();
		int max = summary.getMax();

		String ret = "";
		for (int i = min; i <= max; i++) {
			ret += getCell(i).toString();
		}
		return ret;
	}
}
