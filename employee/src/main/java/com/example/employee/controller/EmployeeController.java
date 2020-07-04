package com.example.employee.controller;

import com.example.employee.dao.EmployeeRepository;
import com.example.employee.model.EmployeeVO;
import com.example.employee.service.SecurityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private SecurityServiceImpl securityServiceImpl;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();


    @PostMapping("/employee/save")
    public EmployeeVO createEmployee(@RequestBody EmployeeVO employee) {
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        return employeeRepository.save(employee);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/employee/login")
    public EmployeeVO authEmployee(@RequestBody EmployeeVO employee) {

        EmployeeVO employeeVO = employeeRepository.findByUserName(employee.getUserName());

       /* if(employeeVO == null) {
            return  new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        if(!employeeVO.getPassword().equals(employee.getPassword())){
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }*/
      //  UserDetails userDetails =securityServiceImpl.loadUserByUsername(employee.getUserName());
        System.out.println(employee.toString());
        return employeeVO;
    }

    @PutMapping("/employee/{id}")
    public ResponseEntity<EmployeeVO> updateEmployee(@PathVariable(value = "id") Long id,
                                                     @RequestBody EmployeeVO employeeDetails) throws ResourceNotFoundException {

        EmployeeVO employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + id));

        employee.setEmail(employeeDetails.getEmail());
        employee.setLastName(employeeDetails.getLastName());
        employee.setFirstName(employeeDetails.getFirstName());
        employee.setPhoneNo((employeeDetails.getPhoneNo()));
        final EmployeeVO updatedEmployee = employeeRepository.save(employee);
        return ResponseEntity.ok(updatedEmployee);
    }

    @GetMapping("/employee/{userName}")
    public boolean checkUserExists(@PathVariable(value = "userName") String userName) {

        EmployeeVO employeeVO = employeeRepository.findByUserName(userName);

        if(employeeVO == null) {
            return false;
        }
        return true;
    }

}
