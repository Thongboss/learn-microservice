package com.microservice.employeeservice.command.aggregate;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import com.microservice.employeeservice.command.command.CreateEmployeeCommand;
import com.microservice.employeeservice.command.command.DeleteEmployeeCommand;
import com.microservice.employeeservice.command.command.UpdateEmployeeCommand;
import com.microservice.employeeservice.command.event.EmployeeCreateEvent;
import com.microservice.employeeservice.command.event.EmployeeDeleteEvent;
import com.microservice.employeeservice.command.event.EmployeeUpdateEvent;

@Aggregate
public class EmployeeAggregate {
	@AggregateIdentifier
	private String employeeId;
	private String firstName;
	private String lastName;
	private String kin;
	private Boolean isDisciplined;
	public EmployeeAggregate() {}
	
	@CommandHandler
	public EmployeeAggregate(CreateEmployeeCommand com) {
		EmployeeCreateEvent event = new EmployeeCreateEvent();
		BeanUtils.copyProperties(com, event);
		AggregateLifecycle.apply(event);
	}
	
	@CommandHandler
	public void handle(UpdateEmployeeCommand com) {
		EmployeeUpdateEvent event = new EmployeeUpdateEvent();
		BeanUtils.copyProperties(com, event);
		AggregateLifecycle.apply(event);
	}
	
	@CommandHandler
	public void handle(DeleteEmployeeCommand com) {
		EmployeeDeleteEvent event = new EmployeeDeleteEvent();
		BeanUtils.copyProperties(com, event);
		AggregateLifecycle.apply(event);
	}
	
	@EventSourcingHandler
	public void on(EmployeeCreateEvent event) {
		this.employeeId = event.getEmployeeId();
		this.firstName = event.getFirstName();
		this.lastName = event.getLastName();
		this.kin = event.getKin();
		this.isDisciplined = event.getIsDisciplined();
	}
	
	@EventSourcingHandler
	public void on(EmployeeUpdateEvent event) {
		this.employeeId = event.getEmployeeId();
		this.firstName = event.getFirstName();
		this.lastName = event.getLastName();
		this.kin = event.getKin();
		this.isDisciplined = event.getIsDisciplined();
	}
	
	@EventSourcingHandler
	public void on(EmployeeDeleteEvent event) {
		this.employeeId = event.getEmployeeId();
	}
	
}
