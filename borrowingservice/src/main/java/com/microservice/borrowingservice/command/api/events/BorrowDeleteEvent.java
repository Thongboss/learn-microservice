package com.microservice.borrowingservice.command.api.events;

public class BorrowDeleteEvent {
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BorrowDeleteEvent(String id) {
		super();
		this.id = id;
	}

	public BorrowDeleteEvent() {
		super();
		// TODO Auto-generated constructor stub
	}
}
