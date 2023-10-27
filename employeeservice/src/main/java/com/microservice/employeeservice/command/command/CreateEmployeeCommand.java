package com.microservice.employeeservice.command.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class CreateEmployeeCommand {
	@TargetAggregateIdentifier
	private String employeeId;
	private String firstName;
	private String lastName;
	private String kin;
	private boolean isDisciplined;
	
	public CreateEmployeeCommand() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CreateEmployeeCommand(String employeeId, String firstName, String lastName, String kin,
			boolean isDisciplined) {
		super();
		this.employeeId = employeeId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.kin = kin;
		this.isDisciplined = isDisciplined;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getKin() {
		return kin;
	}
	public void setKin(String kin) {
		this.kin = kin;
	}
	public boolean isDisciplined() {
		return isDisciplined;
	}
	public void setDisciplined(boolean isDisciplined) {
		this.isDisciplined = isDisciplined;
	}
	
	
}
