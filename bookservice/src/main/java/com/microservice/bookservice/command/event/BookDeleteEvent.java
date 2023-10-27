package com.microservice.bookservice.command.event;

public class BookDeleteEvent {
	private String bookId;
	
	public BookDeleteEvent(String bookId) {
		super();
		this.bookId = bookId;
	}
	public String getBookId() {
		return bookId;
	}
	public void setBookId(String bookId) {
		this.bookId = bookId;
	}
}
