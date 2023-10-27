package com.microservice.employeeservice.command.event;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.microservice.employeeservice.command.data.Employee;
import com.microservice.employeeservice.command.data.EmployeeRepository;

@Component
public class EmployeeEventsHandler {
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@EventHandler
	public void on(EmployeeCreateEvent event) {
		Employee employee = new Employee();
		BeanUtils.copyProperties(event, employee);
		employeeRepository.save(employee);
	}
	
	@EventHandler
	public void on(EmployeeUpdateEvent event) {
		Employee edm = employeeRepository.findById(event.getEmployeeId()).get();
		edm.setFirstName(event.getFirstName());
		edm.setLastName(event.getLastName());
		edm.setKin(event.getKin());
		edm.setDisciplined(event.isDisciplined());
		employeeRepository.save(edm);
	}
	
	@EventHandler
	public void on(EmployeeDeleteEvent event) {
		employeeRepository.deleteById(event.getEmployeeId());
	}
}
