package com.microservice.borrowingservice.command.api.saga;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import com.microservice.borrowingservice.command.api.command.DeleteBorrowCommand;
import com.microservice.borrowingservice.command.api.events.BorrowCreateEvent;
import com.microservice.commonservice.command.UpdateStatusBookCommand;
import com.microservice.commonservice.model.BookResponseCommonModel;
import com.microservice.commonservice.query.GetBookDetailsQuery;

@Saga
public class BorrowingSaga {
	@Autowired
	private transient CommandGateway commandGateway;
	
	@Autowired
	private transient QueryGateway queryGateway;
	
	@StartSaga
	@SagaEventHandler(associationProperty = "id") // associationProperty định nghĩa khóa chính tương tự @TargetAggregateIdentifier
	private void handler(BorrowCreateEvent event) {
		System.out.println("BorrowCreateEvent with bookId: "+event.getBookId() + "and employeeId: "+event.getEmployeeId());
		
		try {
			SagaLifecycle.associateWith("bookId",event.getBookId());
			
			GetBookDetailsQuery getBookDetailsQuery = new GetBookDetailsQuery(event.getBookId());
			
			BookResponseCommonModel model = 
					queryGateway.query(getBookDetailsQuery, ResponseTypes.instanceOf(BookResponseCommonModel.class)).join();
			if(model.getIsReady() == true) {
				UpdateStatusBookCommand command = 
						new UpdateStatusBookCommand(event.getBookId(), false, event.getEmployeeId(), event.getId());
		        commandGateway.sendAndWait(command);
			}else {
				throw new Exception("Sách đã có thằng tậu");
			}
		} catch (Exception e) {
			this.rollBackBorrowRecord(event.getId());
			System.out.println("Saga exception: "+e.getMessage());
		}
	}
	
	private void rollBackBorrowRecord(String id) {
		commandGateway.sendAndWait(new DeleteBorrowCommand(id));
	}
}
