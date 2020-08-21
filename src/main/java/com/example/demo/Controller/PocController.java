package com.example.demo.Controller;

import com.example.demo.Entity.Employee;
import com.example.demo.Repo.EmployeeRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class PocController {
	Logger log = LoggerFactory.getLogger(PocController.class);

	@Autowired
	private EmployeeRepo employeeRepo;

	@Cacheable(cacheNames = "myControlledCache", key = "'employees'")
	@GetMapping("/employees")
	public List<Employee> getAllEmployees() {
		long start = System.currentTimeMillis();
		log.info("not found in cache getting from db");
		try {
			Thread.sleep(1000 * 10);
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
		List<Employee> employees = employeeRepo.findAll();
		long end = System.currentTimeMillis();
		long diff = end-start;
		log.info("processing time" + diff);
		return employees;
	}

	@PostMapping("/employees")
	public Employee createEmployee() {
		Employee employee = new Employee("first1","last1");
		return employeeRepo.save(employee);
	}

}
