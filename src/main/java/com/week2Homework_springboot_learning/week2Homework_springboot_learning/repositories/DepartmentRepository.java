package com.week2Homework_springboot_learning.week2Homework_springboot_learning.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.week2Homework_springboot_learning.week2Homework_springboot_learning.entities.DepartmentEntity;

@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Long> {

}
