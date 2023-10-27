package com.microservice.employeeservice.query.projection;

import java.util.ArrayList;
import java.util.List;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.microservice.employeeservice.command.data.Employee;
import com.microservice.employeeservice.command.data.EmployeeRepository;
import com.microservice.employeeservice.query.model.EmployeeResponseModel;
import com.microservice.employeeservice.query.queries.GetAllEmployeeQuery;
import com.microservice.employeeservice.query.queries.GetEmployeeQuery;

@Component
public class EmployeeProjection {
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@QueryHandler
	public EmployeeResponseModel handle(GetEmployeeQuery query) {
		EmployeeResponseModel model = new EmployeeResponseModel();
		Employee employee = employeeRepository.findById(query.getEmployeeId()).get();
		BeanUtils.copyProperties(employee, model);
		return model;
	}
	
	@QueryHandler
	public List<EmployeeResponseModel> handle(GetAllEmployeeQuery all){
		List<EmployeeResponseModel> list = new ArrayList<>();
		List<Employee> listEntity = employeeRepository.findAll();
		listEntity.stream().forEach(employee -> {
			EmployeeResponseModel model = new EmployeeResponseModel();
			BeanUtils.copyProperties(employee, model);
			list.add(model);
		});
		return list;
	}
}
