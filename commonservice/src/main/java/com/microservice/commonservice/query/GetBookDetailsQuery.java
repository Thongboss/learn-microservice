package com.microservice.commonservice.query;

public class GetBookDetailsQuery {
	private String bookId;

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public GetBookDetailsQuery(String bookId) {
		super();
		this.bookId = bookId;
	}
	
	
}
