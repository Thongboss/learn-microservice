package com.microservice.bookservice.query.projection;

import java.util.ArrayList;
import java.util.List;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.microservice.bookservice.command.data.Book;
import com.microservice.bookservice.command.data.BookRepository;
import com.microservice.bookservice.query.model.BookResponseModel;
import com.microservice.bookservice.query.queries.GetAllBookQuery;
import com.microservice.bookservice.query.queries.GetBookQuery;
import com.microservice.commonservice.model.BookResponseCommonModel;
import com.microservice.commonservice.query.GetBookDetailsQuery;

@Component
public class BookProjection {
	@Autowired
	private BookRepository bookRepository;
	
	@QueryHandler
	public BookResponseModel handler(GetBookQuery getBookQuery) {
		BookResponseModel bookResponseModel = new BookResponseModel();
		
		Book book = bookRepository.findById(getBookQuery.getBookId()).get();
		
		BeanUtils.copyProperties(book, bookResponseModel);
		
		return bookResponseModel;
	}
	
	@QueryHandler
	public List<BookResponseModel> handler(GetAllBookQuery getAllBookQuery){
		List<Book> listEntity = bookRepository.findAll();
		List<BookResponseModel> listModel = new ArrayList<>();
		
		listEntity.forEach(b -> {
			BookResponseModel book = new BookResponseModel();
			BeanUtils.copyProperties(b, book);
			listModel.add(book);
		});
		return listModel;
	}
	
	@QueryHandler
	public BookResponseCommonModel handle(GetBookDetailsQuery query) {
		BookResponseCommonModel model = new BookResponseCommonModel();
		Book book = bookRepository.findById(query.getBookId()).get();
		BeanUtils.copyProperties(book, model);
		return model;
	}
}
