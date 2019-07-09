package edu.mum.spreadsheet;

import java.util.HashMap;
import java.util.Map;

public class Sheet {
	private Map<Integer, Row> data = new HashMap<>();

	public Row getRow(int row) {
		if(!data.containsKey(row))
		{
			data.put(row, new SolidRow());
		}
		return data.get(row);
	}

	public Cell getCell(int row, int column)
	{
		return this.getRow(row).getCell(column);
	}
	public void setNumberValue(int row, int column, Number val)
	{
		this.getCell(row, column).setValue(val);
	}

	public void setStringValue(int row, int column, String val)
	{
		this.getCell(row, column).setValue(val);
	}
	
	public void setExpression(int row, int column, String expression)
	{
		this.getCell(row, column).setExpression(expression);
	}
}
