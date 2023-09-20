package com.example.postgresdemo.repository;

import com.example.postgresdemo.model.CourseType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseTypeRepository extends JpaRepository<CourseType, Long> {
}
