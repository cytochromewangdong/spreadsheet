package edu.mum.spreadsheet.observer;

public class Event<T> {
	private int eventType;
	private T param;

	public int getEventType() {
		return eventType;
	}

	public T getParam() {
		return param;
	}

	public Event(int eventType, T param) {
		super();
		this.eventType = eventType;
		this.param = param;
	}
}
