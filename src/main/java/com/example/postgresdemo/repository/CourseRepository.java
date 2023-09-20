package com.example.postgresdemo.repository;

import com.example.postgresdemo.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByCourseTypeIdAndCourseDurationIdAndCoursePaymentId(Long coursetypeId, Long coursedurationId, Long coursepaymentId);
}
