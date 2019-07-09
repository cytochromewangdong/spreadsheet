package edu.mum.spreadsheet;

import edu.mum.spreadsheet.observer.Event;

public interface ChangeListener {
	public void onChange(Event event);
}
