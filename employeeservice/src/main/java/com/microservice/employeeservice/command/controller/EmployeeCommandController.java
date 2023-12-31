package com.microservice.employeeservice.command.controller;

import java.util.UUID;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.employeeservice.command.command.CreateEmployeeCommand;
import com.microservice.employeeservice.command.command.DeleteEmployeeCommand;
import com.microservice.employeeservice.command.command.UpdateEmployeeCommand;
import com.microservice.employeeservice.command.model.EmployeeRequestModel;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeCommandController {
	@Autowired
	private CommandGateway commandGateway;
	
//	@Autowired
//	private MessageChannel output;
	
	@Autowired
	private StreamBridge streamBridge;
	
	@PostMapping
	public String addEmployee(@RequestBody EmployeeRequestModel employee) {
		CreateEmployeeCommand command = 
				new CreateEmployeeCommand(UUID.randomUUID().toString(), employee.getFirstName(), employee.getLastName(), employee.getKin(), false);
		commandGateway.sendAndWait(command);
		return "add employee";
	}
	
	@PutMapping
	public String updateEmployee(@RequestBody EmployeeRequestModel model) {
		UpdateEmployeeCommand command = 
				new UpdateEmployeeCommand(model.getEmployeeId(), model.getFirstName(), model.getLastName(), model.getKin(), model.getIsDisciplined());
		commandGateway.sendAndWait(command);
		return "update employee";
	}
	
	@DeleteMapping("/{employeeId}")
	public String deleteEmloyee(@PathVariable String employeeId) {
		DeleteEmployeeCommand command = new DeleteEmployeeCommand(employeeId);
		commandGateway.sendAndWait(command);
		return "delete employee";
	}
	
	@PostMapping("/sendMessage")
	public void sendMessage(@RequestBody String message) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(message);
//			output.send(MessageBuilder.withPayload(json).build());
			streamBridge.send("microservice-out-0", MessageBuilder.withPayload(json).build());
		} catch (JsonProcessingException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
}
