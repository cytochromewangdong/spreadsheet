package edu.mum.spreadsheet;

public class StringCell extends Cell {

	@Override
	public String getValue() {
		return this.getRawString();
	}

	@Override
	public Number getNumberValue() {
		return 0;
	}

}
