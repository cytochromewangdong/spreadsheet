package edu.mum.spreadsheet;

import edu.mum.spreadsheet.observer.Event;

public interface StatusChangeListener<T> {
	public void onChange(Event<T> event);
}
