package com.microservice.borrowingservice.command.api.aggregate;

import java.util.Date;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import com.microservice.borrowingservice.command.api.command.CreateBorrowCommand;
import com.microservice.borrowingservice.command.api.command.DeleteBorrowCommand;
import com.microservice.borrowingservice.command.api.events.BorrowCreateEvent;
import com.microservice.borrowingservice.command.api.events.BorrowDeleteEvent;

@Aggregate
public class BorrowAggregate {
	@AggregateIdentifier
	private String id;
	private String bookId;
	private String employeeId;
	private Date borrowingDate;
	private Date returnDate;
	
	private String message;

	public BorrowAggregate() {}
	
	@CommandHandler
	public BorrowAggregate (CreateBorrowCommand command) {
		BorrowCreateEvent event = new BorrowCreateEvent();
		BeanUtils.copyProperties(command, event);
		AggregateLifecycle.apply(event);
	}
	
	@EventSourcingHandler
	public void on(BorrowCreateEvent event) {
		this.bookId = event.getBookId();
		this.employeeId = event.getEmployeeId();
		this.id = event.getId();
		this.borrowingDate = event.getBorrowingDate();
	}
	
	@CommandHandler
	public void handle (DeleteBorrowCommand command) {
		BorrowDeleteEvent event = new BorrowDeleteEvent();
		BeanUtils.copyProperties(command, event);
		AggregateLifecycle.apply(event);
	}
	
	@EventSourcingHandler
	public void on(BorrowDeleteEvent event) {
		this.id = event.getId();
	}
}
