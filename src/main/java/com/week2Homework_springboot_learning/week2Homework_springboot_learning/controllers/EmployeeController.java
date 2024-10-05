package com.week2Homework_springboot_learning.week2Homework_springboot_learning.controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.week2Homework_springboot_learning.week2Homework_springboot_learning.dtos.EmployeeDTO;
import com.week2Homework_springboot_learning.week2Homework_springboot_learning.exceptions.ResourceNotFoundException;
import com.week2Homework_springboot_learning.week2Homework_springboot_learning.services.EmployeeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        return new ResponseEntity<>(employeeService.getAllEmployees(), HttpStatus.OK);
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable @Valid Long employeeId) {
        Optional<EmployeeDTO> employeeDTO = employeeService.getEmployeeById(employeeId);
        return employeeDTO.map(employee -> ResponseEntity.ok(employee))
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id : " + employeeId));
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody @Valid EmployeeDTO employeeDTO) {
        return new ResponseEntity<>(employeeService.createEmployee(employeeDTO),
                HttpStatus.CREATED);
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<EmployeeDTO> updateEmployeeById(@PathVariable Long employeeId,
            @RequestBody @Valid EmployeeDTO employeeDTO) {
        return new ResponseEntity<>(employeeService.updateEmployeeById(employeeId, employeeDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Boolean> deleteEmployeeById(@PathVariable Long employeeId) {
        return new ResponseEntity<>(employeeService.deleteEmployeeById(employeeId), HttpStatus.OK);
    }

    @PatchMapping("/{employeeId}")
    public ResponseEntity<EmployeeDTO> updateEmployeePartiallyById(@PathVariable Long employeeId,
            @RequestBody Map<String, Object> employeeDetails) {
        return new ResponseEntity<>(employeeService.updateEmployeePartiallyById(employeeId, employeeDetails),
                HttpStatus.OK);
    }

}