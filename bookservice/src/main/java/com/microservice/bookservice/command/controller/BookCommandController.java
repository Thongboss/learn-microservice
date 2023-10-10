package com.microservice.bookservice.command.controller;

import java.util.UUID;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.bookservice.command.command.CreateBookCommand;
import com.microservice.bookservice.command.model.BookRequestModel;

@RestController
@RequestMapping("/api/v1/books")
public class BookCommandController {
	
	@Autowired
	private CommandGateway commandGateway;
	
	//phương thức thêm mới 1 sách bắt đầu từ đây khi gọi đến API
	// sử dụng @RequestBody để nhận dữ liệu và đổ nó vào object BookRequestModel
	@PostMapping
	public String addBook(@RequestBody BookRequestModel model) {
		// sử dụng đối tượng CreateBookCommand để tạo sự kiện command
		// UUID.randomUUID() được sử dụng để generate ID
		CreateBookCommand command = new CreateBookCommand(UUID.randomUUID().toString(), model.getName(), model.getAuthor(), true);
		
		// commandGateway.sendAndWait được sử dụng để gửi command này sang BookAggregate để thực hiện event
		commandGateway.sendAndWait(command);
		return "added Book";
	}
}
