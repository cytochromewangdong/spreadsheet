package edu.mum.spreadsheet;

public abstract class Row extends ContainedSubject<Row> {
	protected final int row;
	public Row(SolidSheet container, int row) {
		super(container);
		this.row = row;
	}

	public abstract Cell getCell(int index);

	public int getEventType() {
		return EventTypeConstant.ROW_CHANGE_EVENT;
	}
}
