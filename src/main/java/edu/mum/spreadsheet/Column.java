package edu.mum.spreadsheet;

public class Column extends ContainedSubject<Column> {
	protected final int column;

	public Column(SpreadSheet container, int column) {
		super(container);
		this.column = column;
	}

	@Override
	public int getEventType() {
		return 0;
	}

	public Cell getCell(int row) {
		return this.getContainer().getCell(row, column);
	}
}
