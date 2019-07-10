package edu.mum.spreadsheet;

import edu.mum.spreadsheet.observer.Subject;

public abstract class ContainedSubject<T> extends Subject<T> implements Contained {
	protected final Sheet container;

	public ContainedSubject(Sheet container) {
		this.container = container;
	}
	@Override
	public Sheet getContainer() {
		return container;
	}

}
