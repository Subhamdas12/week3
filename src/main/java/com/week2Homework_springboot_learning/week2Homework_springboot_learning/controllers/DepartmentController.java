package com.week2Homework_springboot_learning.week2Homework_springboot_learning.controllers;

import java.util.List;
import java.util.Map;

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

import com.week2Homework_springboot_learning.week2Homework_springboot_learning.dtos.DepartmentDTO;
import com.week2Homework_springboot_learning.week2Homework_springboot_learning.services.DepartmentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/department")
public class DepartmentController {
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping(path = "/{departmentId}")
    public ResponseEntity<DepartmentDTO> getDepartmentById(@PathVariable Long departmentId) {
        return ResponseEntity.ok(departmentService.getDepartmentById(departmentId));
    }

    @GetMapping
    public ResponseEntity<List<DepartmentDTO>> getAllDepartment() {
        return ResponseEntity.ok(departmentService.getAllDepartment());
    }

    @PostMapping
    public ResponseEntity<DepartmentDTO> createDepartment(@RequestBody @Valid DepartmentDTO departmentDTO) {
        return new ResponseEntity<>(departmentService.createDepartment(departmentDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{departmentId}")
    public ResponseEntity<DepartmentDTO> updateDepartmentById(@PathVariable Long departmentId,
            @RequestBody @Valid DepartmentDTO departmentDTO) {
        return new ResponseEntity<>(departmentService.updateDepartmentById(departmentId, departmentDTO), HttpStatus.OK);
    }

    @PatchMapping("/{departmentId}")
    public ResponseEntity<DepartmentDTO> updateDepartmentParticalById(@PathVariable Long departmentId,
            @RequestBody Map<String, Object> updates) {
        return new ResponseEntity<>(departmentService.updateDepartmentParticalById(departmentId, updates),
                HttpStatus.OK);
    }

    @DeleteMapping("/{departmentId}")
    public ResponseEntity<Boolean> deleteDepartmentById(@PathVariable Long departmentId) {
        return new ResponseEntity<>(departmentService.deleteDepartmentById(departmentId), HttpStatus.OK);
    }

    @PatchMapping("/{departmentId}/manager/{employeeId}")
    public ResponseEntity<DepartmentDTO> assignManagerToDepartment(@PathVariable Long departmentId,
            @PathVariable Long employeeId) {
        return new ResponseEntity<>(departmentService.assignManagerToDepartment(departmentId, employeeId),
                HttpStatus.OK);
    }

}
