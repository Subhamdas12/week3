package com.week2Homework_springboot_learning.week2Homework_springboot_learning.services;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.el.util.ReflectionUtil;
import org.modelmapper.ModelMapper;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import com.week2Homework_springboot_learning.week2Homework_springboot_learning.dtos.EmployeeDTO;
import com.week2Homework_springboot_learning.week2Homework_springboot_learning.entities.EmployeeEntity;
import com.week2Homework_springboot_learning.week2Homework_springboot_learning.exceptions.ResourceNotFoundException;
import com.week2Homework_springboot_learning.week2Homework_springboot_learning.repositories.EmployeeRepository;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    public void isEmployeeExistsById(Long employeeId) {
        boolean isExists = employeeRepository.existsById(employeeId);
        if (!isExists)
            throw new ResourceNotFoundException("Employee not found with id : " + employeeId);
    }

    public Optional<EmployeeDTO> getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .map(employeeEntity -> modelMapper.map(employeeEntity, EmployeeDTO.class));
    }

    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        EmployeeEntity saveEmployeeEntity = modelMapper.map(employeeDTO, EmployeeEntity.class);
        EmployeeEntity employeeEntity = employeeRepository.save(saveEmployeeEntity);
        return modelMapper.map(employeeEntity, EmployeeDTO.class);
    }

    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(employeeEntity -> modelMapper.map(employeeEntity, EmployeeDTO.class)).collect(Collectors.toList());
    }

    public EmployeeDTO updateEmployeeById(Long employeeId, EmployeeDTO employeeDTO) {
        isEmployeeExistsById(employeeId);
        EmployeeEntity saveEmployeeEntity = modelMapper.map(employeeDTO, EmployeeEntity.class);
        saveEmployeeEntity.setId(employeeId);
        return modelMapper.map(employeeRepository.save(saveEmployeeEntity), EmployeeDTO.class);
    }

    public boolean deleteEmployeeById(Long employeeId) {
        isEmployeeExistsById(employeeId);
        employeeRepository.deleteById(employeeId);
        return true;
    }

    public EmployeeDTO updateEmployeePartiallyById(Long employeeId, Map<String, Object> employeeDetails) {
        isEmployeeExistsById(employeeId);
        EmployeeEntity employeeEntity = employeeRepository.findById(employeeId).get();
        employeeDetails.forEach((key, value) -> {
            System.out.println("key" + key + "value" + value);
            Field fieldToUpdate = ReflectionUtils.findRequiredField(EmployeeEntity.class, key);
            fieldToUpdate.setAccessible(true);
            ReflectionUtils.setField(fieldToUpdate, employeeEntity, value);
        });

        return modelMapper.map(employeeRepository.save(employeeEntity), EmployeeDTO.class);
    }

}
