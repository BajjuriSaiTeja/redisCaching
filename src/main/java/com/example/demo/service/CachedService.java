package com.example.demo.service;

import com.example.demo.Entity.Employee;
import com.example.demo.Repo.EmployeeRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CachedService {
	Logger log = LoggerFactory.getLogger(CachedService.class);

	@Autowired
	private EmployeeRepo employeeRepo;

	@Cacheable(cacheNames = "myControlledCache", key = "'myPrefix_'.concat(#relevant)")
	public List<Employee> getAll(String relevant) {
		log.info("not found in cache getting from db");
		return employeeRepo.findAll();
	}

}
