package com.microservice.employeeservice.command.event;

public class EmployeeDeleteEvent {
	public EmployeeDeleteEvent() {
		super();
		// TODO Auto-generated constructor stub
	}

	private String employeeId;

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public EmployeeDeleteEvent(String employeeId) {
		super();
		this.employeeId = employeeId;
	}
	
}
