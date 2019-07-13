package edu.mum.spreadsheet.observer;

import java.util.LinkedHashSet;
import java.util.Set;

import edu.mum.spreadsheet.StatusChangeListener;

public abstract class Subject<T> {
	Set<StatusChangeListener<T>> list = new LinkedHashSet<>();

	public void registerListener(StatusChangeListener<T> listener) {
		list.add(listener);
	}

	public void removeListener(StatusChangeListener<T> listener) {
		list.remove(listener);
	}

	private boolean notifying;

	public void postAll(T param) {
		if (!notifying) {
			notifying = true;
			try {
				Event<T> e = new Event<>(getEventType(), param);
				list.forEach(l -> l.onChange(e));
			} finally {
				notifying = false;
			}
		}
	}

	public abstract int getEventType();
}
