package edu.mum.spreadsheet;

public abstract class Sheet {
	public abstract Row getRow(int row);
	public void beforeCellChange(Cell cell) {
		
	}
	public void afterCellChange(Cell cell) {
		
	}

}
