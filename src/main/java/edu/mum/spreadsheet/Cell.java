package edu.mum.spreadsheet;

import lombok.Data;

@Data
public abstract class Cell {
	private String rawString;
	public abstract String getValue();
	public  abstract Number getNumberValue();
}
