package edu.mum.spreadsheet;

import edu.mum.spreadsheet.observer.Subject;

public abstract class ContainedSubject<T> extends Subject<T> implements Contained {
	protected final SpreadSheet container;

	public ContainedSubject(SpreadSheet container) {
		this.container = container;
	}
	@Override
	public SpreadSheet getContainer() {
		return container;
	}

}
