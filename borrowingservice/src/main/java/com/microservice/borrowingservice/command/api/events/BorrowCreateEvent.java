package com.microservice.borrowingservice.command.api.events;

import java.util.Date;

public class BorrowCreateEvent {
	private String id;
	private String bookId;
	private String employeeId;
	private Date borrowingDate;
	public BorrowCreateEvent(String id, String bookId, String employeeId, Date borrowingDate) {
		super();
		this.id = id;
		this.bookId = bookId;
		this.employeeId = employeeId;
		this.borrowingDate = borrowingDate;
	}
	public BorrowCreateEvent() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBookId() {
		return bookId;
	}
	public void setBookId(String bookId) {
		this.bookId = bookId;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public Date getBorrowingDate() {
		return borrowingDate;
	}
	public void setBorrowingDate(Date borrowingDate) {
		this.borrowingDate = borrowingDate;
	}
	
}
