package edu.mum.spreadsheet;

public class NullCell extends Cell {
	public static NullCell DEFAULT_NULL_CELL = new NullCell();

	private NullCell() {

	}

	@Override
	public String getValue() {
		return null;
	}

	@Override
	public Number getNumberValue() {
		return 0;
	}

}
