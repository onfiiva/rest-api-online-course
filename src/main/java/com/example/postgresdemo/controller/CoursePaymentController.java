package com.example.postgresdemo.controller;

import com.example.postgresdemo.exception.ResourceNotFoundException;
import com.example.postgresdemo.model.CoursePayment;
import com.example.postgresdemo.repository.CoursePaymentRepository;
import com.example.postgresdemo.repository.CoursePaymentTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CoursePaymentController {
    @Autowired
    private CoursePaymentRepository coursePaymentRepository;

    @Autowired
    private CoursePaymentTypeRepository coursePaymentTypeRepository;

    @GetMapping("/coursepaymenttypes/{typeId}/coursepayments")
    public List<CoursePayment> getCoursePaymentsByPaymentType(@PathVariable Long typeId) {
        return coursePaymentRepository.findByTypeId(typeId);
    }


    @PostMapping("/coursepaymenttypes/{typeId}/coursepayments")
    public CoursePayment createCoursePayment(@PathVariable Long typeId,
                                             @Valid @RequestBody CoursePayment coursePayment) {
        return coursePaymentTypeRepository.findById(typeId)
                .map(type -> {
                    System.out.println("typeId : " + typeId);
                    System.out.println("type : " + type.getName());
                    coursePayment.setType(type);
                    return coursePaymentRepository.save(coursePayment);
                }).orElseThrow(() -> new ResourceNotFoundException("Payment Type not found with id " + typeId));
    }

    @PutMapping("/coursepaymenttypes/{typeId}/coursepayments/{coursepaymentId}")
    public CoursePayment updateCoursePayment(@PathVariable Long typeId,
                                             @PathVariable Long coursepaymentId,
                                               @Valid @RequestBody CoursePayment coursePaymentRequest) {
        if(!coursePaymentTypeRepository.existsById(typeId)) {
            throw new ResourceNotFoundException("Payment Type not found with id " + typeId);
        }

        return coursePaymentRepository.findById(coursepaymentId)
                .map(coursePayment -> {
                    coursePayment.setSum(coursePaymentRequest.getSum());
                    return coursePaymentRepository.save(coursePayment);
                }).orElseThrow(() -> new ResourceNotFoundException("Course Payment not found with id " + coursepaymentId));
    }


    @DeleteMapping("/coursepaymenttypes/{typeId}/coursepayments/{coursepaymentId}")
    public ResponseEntity<?> deleteCoursePayment(@PathVariable Long typeId,
                                                 @PathVariable Long coursepaymentId) {
        if(!coursePaymentTypeRepository.existsById(typeId)) {
            throw new ResourceNotFoundException("Payment Type not found with id " + typeId);
        }

        return coursePaymentRepository.findById(coursepaymentId)
                .map(coursePayment -> {
                    coursePaymentRepository.delete(coursePayment);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Course Payment not found with id " + coursepaymentId));
    }
}
