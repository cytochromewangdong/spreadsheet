package edu.mum.spreadsheet.observer;

import java.util.LinkedHashSet;
import java.util.Set;

import edu.mum.spreadsheet.ChangeListener;

public abstract class Subject<T> {
	Set<ChangeListener<T>> list = new LinkedHashSet<>();
	public void registerListener(ChangeListener<T> listener) {
		list.add(listener);
	}
	public void removeListener(ChangeListener<T> listener)
	{
		list.remove(listener);
	}
	public void postAll(T param)
	{
		Event<T> e = new Event<>(getEventType(), param) ;
		list.forEach(l->l.onChange(e));
	}
	public abstract int getEventType();
}
