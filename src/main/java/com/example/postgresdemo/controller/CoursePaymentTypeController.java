package com.example.postgresdemo.controller;

import com.example.postgresdemo.exception.ResourceNotFoundException;
import com.example.postgresdemo.model.CoursePaymentType;
import com.example.postgresdemo.repository.CoursePaymentTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class CoursePaymentTypeController {
    @Autowired
    private CoursePaymentTypeRepository coursePaymentTypeRepository;

    @GetMapping("/coursepaymenttypes")
    public Page<CoursePaymentType> getCoursePaymentTypes(Pageable pageable) {
        return coursePaymentTypeRepository.findAll(pageable);
    }


    @PostMapping("/coursepaymenttypes")
    public CoursePaymentType createCoursePaymentType(@Valid @RequestBody CoursePaymentType coursePaymentType) {
        return coursePaymentTypeRepository.save(coursePaymentType);
    }

    @PutMapping("/coursepaymenttypes/{id}")
    public CoursePaymentType updateCoursePaymentType(@PathVariable Long id,
                                              @Valid @RequestBody CoursePaymentType coursePaymentTypeRequest) {
        return coursePaymentTypeRepository.findById(id)
                .map(coursePaymentType -> {
                    coursePaymentType.setName(coursePaymentTypeRequest.getName());
                    return coursePaymentTypeRepository.save(coursePaymentType);
                }).orElseThrow(() -> new ResourceNotFoundException("Course Payment Type not found with id " + id));
    }


    @DeleteMapping("/coursepaymenttypes/{id}")
    public ResponseEntity<?> deleteCoursePaymentType(@PathVariable Long id) {
        return coursePaymentTypeRepository.findById(id)
                .map(coursePaymentType -> {
                    coursePaymentTypeRepository.delete(coursePaymentType);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Course Payment Type not found with id " + id));
    }
}
