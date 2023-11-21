package com.microservice.borrowingservice.command.api.events;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.microservice.borrowingservice.command.api.data.BorrowRepository;
import com.microservice.borrowingservice.command.api.data.Borrowing;

@Component
public class BorrowEventsHandler {
	@Autowired
	private BorrowRepository borrowRepository;
	
	@EventHandler
	public void on(BorrowCreateEvent event) {
		Borrowing borrow = new Borrowing();
		BeanUtils.copyProperties(event, borrow);
		borrowRepository.save(borrow);
	}
}
