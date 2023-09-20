package com.example.postgresdemo.repository;

import com.example.postgresdemo.model.CoursePaymentType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoursePaymentTypeRepository extends JpaRepository<CoursePaymentType, Long> {
}
