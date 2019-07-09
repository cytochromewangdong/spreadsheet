package edu.mum.spreadsheet;

import java.util.HashMap;
import java.util.Map;

public class SolidRow extends Row {
	private Map<Integer, Cell> data = new HashMap<>();

	public Cell getCell(int column) {
		if(!data.containsKey(column)) {
			data.put(column, new SolidCell());
		}
		return data.get(column);			
	}
}
