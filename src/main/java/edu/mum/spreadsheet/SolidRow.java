package edu.mum.spreadsheet;

import java.util.HashMap;
import java.util.Map;

public class SolidRow extends Row {
	private Map<Integer, Cell> data = new HashMap<>();

	public Cell getCell(int index) {
		return data.containsKey(index) ? data.get(index) : NullCell.DEFAULT_NULL_CELL;
	}
}
