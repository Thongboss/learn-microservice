package com.microservice.bookservice.command.aggregate;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import com.microservice.bookservice.command.command.CreateBookCommand;
import com.microservice.bookservice.command.command.DeleteBookCommand;
import com.microservice.bookservice.command.command.UpdateBookCommand;
import com.microservice.bookservice.command.event.BookCreateEvent;
import com.microservice.bookservice.command.event.BookDeleteEvent;
import com.microservice.bookservice.command.event.BookUpdateEvent;
import com.microservice.commonservice.command.UpdateStatusBookCommand;
import com.microservice.commonservice.events.UpdateStatusBookEvent;

// @Aggregate là chú thích cụ thể của Axon Spring đánh dấu lớp này là tổng hợp
@Aggregate
public class BookAggregate {
	
	// Vì một tổng hợp sẽ xử lý các lệnh được nhắm mục tiêu đến một phiên bản tổng hợp cụ thể, chúng ta cần chỉ định mã định danh bằng chú thích AggregateIdentifier .
	@AggregateIdentifier
	private String bookId;
	private String name;
	private String author;
	private Boolean isReady;
	public BookAggregate() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	// Để cho framework biết rằng hàm đã cho có thể xử lý các lệnh, chúng tôi sẽ thêm chú thích CommandHandler .
	@CommandHandler
	public BookAggregate(CreateBookCommand createBookCommand) {
		BookCreateEvent bookCreateEvent = new BookCreateEvent();
		
		BeanUtils.copyProperties(createBookCommand, bookCreateEvent);
		
		/* Khi xử lý CreateBookCommand , nó sẽ thông báo cho phần còn lại của ứng dụng rằng một đơn hàng đã được tạo bằng cách xuất bản BookCreatedEvent .
		 *  Để xuất bản một sự kiện từ trong một tổng hợp, chúng tôi sẽ sử dụng  AggregateLifecycle#apply(Object…) . */
		
		AggregateLifecycle.apply(bookCreateEvent);
		
	}
	
	@CommandHandler
	public void handle(UpdateBookCommand updateBookCommand) {
		BookUpdateEvent bookUpdateEvent = new BookUpdateEvent();
		
		BeanUtils.copyProperties(updateBookCommand, bookUpdateEvent);
		
		AggregateLifecycle.apply(bookUpdateEvent);
	}
	
	@CommandHandler
	public void handle(DeleteBookCommand deleteBookCommand) {
		BookDeleteEvent bookDeleteEvent = new BookDeleteEvent(deleteBookCommand.getBookId());
		
//		BeanUtils.copyProperties(updateBookCommand, bookUpdateEvent);
		
		AggregateLifecycle.apply(bookDeleteEvent);
	}
	
	// sau khi thực hiện mượn sách ở borrow nó sẽ chạy vào commonservice thông qua commonservice mapping qua đây để thực hiện update trạng thái sách
	@CommandHandler
	public void handle(UpdateStatusBookCommand command) {
		UpdateStatusBookEvent book = new UpdateStatusBookEvent();
		BeanUtils.copyProperties(command, book);
		AggregateLifecycle.apply(book);
	}
	// sau khi chạy ở commandhandler nó thông qua eventsourcing này để truyền tải thông tin update
	@EventSourcingHandler
	public void on(UpdateStatusBookEvent event) {
		this.bookId = event.getBookId();
		this.isReady = event.getIsReady();
	}
	
	/* sau khi sự kiện command tại @CommandHandler được chạy, dữ liệu event được gửi từ AggregateLifecycle.apply nó sẽ thay đổi giá trị 
	 * thuộc tính của lớp BookAggregate đang có thông qua hàm on dưới anotation @EventSourcingHandler. từ đây nó có thể phát hiện sự thay đổi của thuộc tính
	 * ví dụ: khi update 1 hoặc 2 trường và đó là trường nào thì nó sẽ ghi log lại */
	@EventSourcingHandler
	public void on(BookCreateEvent event) {
		this.bookId = event.getBookId();
		this.author = event.getAuthor();
		this.isReady = event.getIsReady();
		this.name = event.getName();
	}
	
	@EventSourcingHandler
	public void on(BookUpdateEvent event) {
		this.bookId = event.getBookId();
		this.author = event.getAuthor();
		this.isReady = event.getIsReady();
		this.name = event.getName();
	}
	
	@EventSourcingHandler
	public void on(BookDeleteEvent event) {
		this.bookId = event.getBookId();
	}
}
