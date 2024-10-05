package com.week2Homework_springboot_learning.week2Homework_springboot_learning.services;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import com.week2Homework_springboot_learning.week2Homework_springboot_learning.dtos.DepartmentDTO;
import com.week2Homework_springboot_learning.week2Homework_springboot_learning.entities.DepartmentEntity;
import com.week2Homework_springboot_learning.week2Homework_springboot_learning.entities.EmployeeEntity;
import com.week2Homework_springboot_learning.week2Homework_springboot_learning.exceptions.ResourceNotFoundException;
import com.week2Homework_springboot_learning.week2Homework_springboot_learning.repositories.DepartmentRepository;
import com.week2Homework_springboot_learning.week2Homework_springboot_learning.repositories.EmployeeRepository;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;
    private final EmployeeService employeeService;
    private final ModelMapper modelMapper;

    public DepartmentService(DepartmentRepository departmentRepository, ModelMapper modelMapper,
            EmployeeService employeeService, EmployeeRepository employeeRepository) {
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
        this.employeeService = employeeService;
    }

    private void isDepartmentExistsById(Long departmentId) {
        boolean isExists = departmentRepository.existsById(departmentId);
        if (!isExists)
            throw new ResourceNotFoundException("No Department found with id : " + departmentId);
    }

    public DepartmentDTO getDepartmentById(Long departmentId) {
        DepartmentEntity departmentEntity = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("No department found with id " + departmentId));
        return modelMapper.map(departmentEntity, DepartmentDTO.class);
    }

    public DepartmentDTO createDepartment(DepartmentDTO departmentDTO) {
        DepartmentEntity saveDepartmentEntity = modelMapper.map(departmentDTO, DepartmentEntity.class);
        return modelMapper.map(departmentRepository.save(saveDepartmentEntity), DepartmentDTO.class);
    }

    public DepartmentDTO updateDepartmentById(Long departmentId, DepartmentDTO departmentDTO) {
        isDepartmentExistsById(departmentId);
        DepartmentEntity saveDepartmentEntity = modelMapper.map(departmentDTO, DepartmentEntity.class);
        saveDepartmentEntity.setId(departmentId);
        return modelMapper.map(departmentRepository.save(saveDepartmentEntity), DepartmentDTO.class);
    }

    public List<DepartmentDTO> getAllDepartment() {
        List<DepartmentEntity> departmentEntity = departmentRepository.findAll();
        return departmentEntity.stream().map(entity -> modelMapper.map(entity, DepartmentDTO.class))
                .collect(Collectors.toList());
    }

    public boolean deleteDepartmentById(Long departmentId) {
        isDepartmentExistsById(departmentId);
        departmentRepository.deleteById(departmentId);
        return true;
    }

    public DepartmentDTO updateDepartmentParticalById(Long departmentId, Map<String, Object> updates) {
        isDepartmentExistsById(departmentId);
        DepartmentEntity departmentEntity = departmentRepository.findById(departmentId).get();
        updates.forEach((key, value) -> {
            System.out.println("key" + key + "value" + value);
            Field field = ReflectionUtils.findRequiredField(DepartmentEntity.class, key);
            field.setAccessible(true);
            ReflectionUtils.setField(field, departmentEntity, value);
        });
        return modelMapper.map(departmentRepository.save(departmentEntity), DepartmentDTO.class);
    }

    public DepartmentDTO assignManagerToDepartment(Long departmentId, Long employeeId) {
        isDepartmentExistsById(departmentId);
        employeeService.isEmployeeExistsById(employeeId);
        DepartmentEntity departmentEntity = departmentRepository.findById(departmentId).get();
        EmployeeEntity employeeEntity = employeeRepository.findById(employeeId).get();
        departmentEntity.setManager(employeeEntity);
        return modelMapper.map(departmentRepository.save(departmentEntity), DepartmentDTO.class);
    }
}
