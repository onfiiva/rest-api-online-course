package com.example.postgresdemo.repository;

import com.example.postgresdemo.model.CoursePayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CoursePaymentRepository extends JpaRepository<CoursePayment, Long> {
    List<CoursePayment> findByTypeId(Long typeId);
}
