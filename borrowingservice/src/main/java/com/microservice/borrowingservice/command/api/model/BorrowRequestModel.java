package com.microservice.borrowingservice.command.api.model;

import java.util.Date;

public class BorrowRequestModel {
	private String id;
	private String bookId;
	private String employeeId;
	private Date borrowingDate;
	private Date returnDate;
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
	public Date getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}
	public BorrowRequestModel(String id, String bookId, String employeeId, Date borrowingDate, Date returnDate) {
		super();
		this.id = id;
		this.bookId = bookId;
		this.employeeId = employeeId;
		this.borrowingDate = borrowingDate;
		this.returnDate = returnDate;
	}
	
}
