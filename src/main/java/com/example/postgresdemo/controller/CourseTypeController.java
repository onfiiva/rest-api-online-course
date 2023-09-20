package com.example.postgresdemo.controller;

import com.example.postgresdemo.exception.ResourceNotFoundException;
import com.example.postgresdemo.model.CourseType;
import com.example.postgresdemo.repository.CourseTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class CourseTypeController {
    @Autowired
    private CourseTypeRepository courseTypeRepository;

    @GetMapping("/coursetypes")
    public List<CourseType> getCoursePaymentTypes() {
        return courseTypeRepository.findAll();
    }

    @GetMapping("/coursetypes/{id}")
    public Optional<CourseType> getCoursePaymentTypes(@PathVariable Long id) {
        return courseTypeRepository.findById(id);
    }

    @PostMapping("/coursetypes")
    public CourseType createCoursePaymentType(@Valid @RequestBody CourseType courseType) {
        return courseTypeRepository.save(courseType);
    }

    @PutMapping("/coursetypes/{id}")
    public CourseType updateCoursePaymentType(@PathVariable Long id,
                                                     @Valid @RequestBody CourseType courseTypeRequest) {
        return courseTypeRepository.findById(id)
                .map(courseType -> {
                    courseType.setName(courseTypeRequest.getName());
                    return courseTypeRepository.save(courseType);
                }).orElseThrow(() -> new ResourceNotFoundException("Course Type not found with id " + id));
    }


    @DeleteMapping("/coursetypes/{id}")
    public ResponseEntity<?> deleteCoursePaymentType(@PathVariable Long id) {
        return courseTypeRepository.findById(id)
                .map(courseType -> {
                    courseTypeRepository.delete(courseType);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Course Type not found with id " + id));
    }
}
