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
		// sau khi thực hiện request đến DB thì nó sẽ nhảy vào borrowingSaga để bắt đầu thực hiện vòng đời saga
	}
	
	@EventHandler
	public void on(BorrowDeleteEvent event) {
		if(borrowRepository.findById(event.getId()).isPresent()) {
			borrowRepository.deleteById(event.getId());
		}
		else return;
	}
}
