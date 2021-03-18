package com.vempalli.contoller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vempalli.customException.CustomException;
import com.vempalli.model.Employee;
import com.vempalli.repository.EmployeeRepository;

@RestController
@RequestMapping("/api/v1")
//@CrossOrigin("http://localhost:4200")
public class EmployeeController {

	@Autowired
	private EmployeeRepository employeeRepository;

	//get all employees

	@GetMapping("/employees")
	public List<Employee> getAllEmployees()
	{
		return employeeRepository.findAll();
	}

	//create a new Employee

	@PostMapping("/saveEmployee")
	public Employee createEmployee(@RequestBody Employee emp)
	{
		return employeeRepository.save(emp);	
	}

	//get Employee by id
	@GetMapping("/getEmployee/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id)
	{
		Employee emp1=employeeRepository.findById(id).orElseThrow(()->new CustomException("The employee with id:"+id+"not present"));
		return ResponseEntity.ok(emp1);

	}

	// Updating emp details
	@PutMapping("/updateEmployee/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable Long id,@RequestBody Employee emp )
	{
		Employee employee=employeeRepository.findById(id).orElseThrow(()->new CustomException("The employee with id:"+id+"not present"));

		employee.setFirstName(emp.getFirstName());
		employee.setLastName(emp.getLastName());
		employee.setEmailId(emp.getEmailId());
		employee.setMobileNumber(emp.getMobileNumber());
		Employee updatedEmployee=employeeRepository.save(employee);
		return ResponseEntity.ok(updatedEmployee);

	}
	//deleteEmployee details based on emp id
	@DeleteMapping("/deleteEmployee/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id)
	{
		Employee employee=employeeRepository.findById(id)
				.orElseThrow(()->new CustomException("The employee with id:"+id+"not present"));

		employeeRepository.delete(employee);
		Map<String, Boolean> map=new HashMap<>();
		map.put("Deleted", true);
		return ResponseEntity.ok(map);
	}
}
