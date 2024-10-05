package com.week2Homework_springboot_learning.week2Homework_springboot_learning.dtos;

import java.time.LocalDate;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDTO {
    @NotBlank(message = "Title of department cannot be blank")
    private String title;
    @AssertTrue(message = "The department should be active")
    private Boolean isActive;
    private LocalDate createdAt;
    private EmployeeDTO manager;
}
