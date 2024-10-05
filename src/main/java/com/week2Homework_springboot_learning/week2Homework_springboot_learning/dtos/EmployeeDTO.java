package com.week2Homework_springboot_learning.week2Homework_springboot_learning.dtos;

import java.time.LocalDate;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {

    @NotBlank(message = "Name of employee cannot be blank")
    private String name;
    @NotBlank(message = "Email of employee cannot be blank")
    @Email(message = "Please enter a valid email")
    private String email;
    @Pattern(regexp = "^(ADMIN|USER)$", message = "Role of employee can either be ADMIN or USER")
    private String role;
    @Min(value = 18, message = "Minimum age of employee should be 18")
    @Max(value = 80, message = "Maximum age of employee should be 80")
    private Integer age;
    @NotNull(message = "Salary of employee should not be null")
    @Positive(message = "Salary of employee should be positive")
    @Digits(integer = 5, fraction = 2, message = "The salary should be in the form of XXXXX.YY")
    @DecimalMax(value = "100000.99", message = "Maximum value of salary should be 100000.99")
    @DecimalMin(value = "100.00", message = "Minimum value of salary should be 100.00")
    private Double salary;
    @PastOrPresent(message = "Date of joining of an employee cannot be in the future")
    private LocalDate dateOfJoining;
    @AssertTrue(message = "Employee should be active")
    private Boolean isActive;
    private DepartmentDTO managedDepartment;
}
