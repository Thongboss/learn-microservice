package com.microservice.bookservice.command.event;

import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.microservice.bookservice.command.data.Book;
import com.microservice.bookservice.command.data.BookRepository;

@Component
public class BookEventsHandler {
	
	@Autowired
	private BookRepository bookRepository;
	
	/* sau khi nó chạy xong hàm on có anotation @EventSourcingHandler ở lớp BookAggregate, spring sẽ tự động quét các lớp component và tìm đến hàm có anotation @EventHandler
	 * để thực hiện nghiệp vụ bên trong hàm, (spring sẽ tự mapping thuộc tính từ lớp BookAggregate chuyển sang)*/
	
	@EventHandler
	public void on(BookCreateEvent event) {
		Book book = new Book();
		
		BeanUtils.copyProperties(event, book);
		
		bookRepository.save(book);
	}
	
	@EventHandler
	public void on(BookUpdateEvent event) {
		Book book = bookRepository.findById(event.getBookId()).get();
		book.setAuthor(event.getAuthor());
		book.setName(event.getName());
		book.setIsReady(event.getIsReady());
		
		bookRepository.save(book);
		
	}
	
	@EventHandler
	public void on(BookDeleteEvent event) {
		bookRepository.deleteById(event.getBookId());
	}
}
