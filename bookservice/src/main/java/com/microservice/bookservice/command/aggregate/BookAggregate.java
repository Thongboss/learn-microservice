package com.microservice.bookservice.command.aggregate;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import com.microservice.bookservice.command.command.CreateBookCommand;
import com.microservice.bookservice.command.event.BookCreateEvent;

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
}
