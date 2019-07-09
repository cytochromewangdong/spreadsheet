package edu.mum.spreadsheet;

public class NullRow extends Row {

	@Override
	public Cell getCell(int index) {
		return NullCell.DEFAULT_NULL_CELL;
	}

}
