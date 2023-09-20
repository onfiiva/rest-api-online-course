package com.example.postgresdemo.controller;

import com.example.postgresdemo.exception.ResourceNotFoundException;
import com.example.postgresdemo.model.Course;
import com.example.postgresdemo.repository.CourseDurationRepository;
import com.example.postgresdemo.repository.CoursePaymentRepository;
import com.example.postgresdemo.repository.CourseRepository;
import com.example.postgresdemo.repository.CourseTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
public class CourseController {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseTypeRepository courseTypeRepository;

    @Autowired
    private CourseDurationRepository courseDurationRepository;

    @Autowired
    private CoursePaymentRepository coursePaymentRepository;

    @GetMapping("/courses")
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @GetMapping("/course/{id}")
    public Optional<Course> getCourseById(@PathVariable Long id) {
        return courseRepository.findById(id);
    }

    @GetMapping("/coursetype/t{coursetypeId}/courseduration/d{coursedurationId}/coursepayment/p{coursepaymentId}/course")
    public List<Course> getCoursesByTypeIdAndDurationdIdAndPaymentId(@PathVariable Long coursetypeId,
                                                                     @PathVariable Long coursedurationId,
                                                                     @PathVariable Long coursepaymentId) {
        return courseRepository.findByCourseTypeIdAndCourseDurationIdAndCoursePaymentId(coursetypeId, coursedurationId, coursepaymentId);
    }

    @PostMapping("/coursetype/t{coursetypeId}/courseduration/d{coursedurationId}/coursepayment/p{coursepaymentId}/course")
    public Course addCourse(@PathVariable Long coursetypeId,
                            @PathVariable Long coursedurationId,
                            @PathVariable Long coursepaymentId,
                            @Valid @RequestBody Course course) {
        return courseTypeRepository.findById(coursetypeId)
                .map(courseType -> {
                    course.setCourseType(courseType);
                    return courseDurationRepository.findById(coursedurationId)
                            .map(courseDuration -> {
                                course.setCourseDuration(courseDuration);
                                return coursePaymentRepository.findById(coursepaymentId)
                                        .map(coursePayment -> {
                                            course.setCoursePayment(coursePayment);
                                            return courseRepository.save(course);
                                        })
                                        .orElseThrow(() -> new ResourceNotFoundException("Course Payment not found with id " + coursepaymentId));
                            })
                            .orElseThrow(() -> new ResourceNotFoundException("Course Duration not found with id " + coursedurationId));
                })
                .orElseThrow(() -> new ResourceNotFoundException("Course Type not found with id " + coursetypeId));
    }


    @PutMapping("/coursetype/t{coursetypeId}/courseduration/d{coursedurationId}/coursepayment/p{coursepaymentId}/course/c{id}")
    public Course updateCourse(@PathVariable Long coursetypeId,
                               @PathVariable Long coursedurationId,
                               @PathVariable Long coursepaymentId,
                               @PathVariable Long id,
                               @Valid @RequestBody Course courseRequest) {

        if(!courseTypeRepository.existsById(coursetypeId)) {
            throw new ResourceNotFoundException("Course Type not found with id " + coursetypeId);
        }

        if(!courseDurationRepository.existsById(coursedurationId)) {
            throw new ResourceNotFoundException("Course Duration not found with id " + coursedurationId);
        }

        if(!coursePaymentRepository.existsById(coursepaymentId)) {
            throw new ResourceNotFoundException("Course Payment not found with id " + coursepaymentId);
        }

        return courseRepository.findById(id)
                .map(course -> {
                    course.setCourseType(courseRequest.getCourseType());
                    course.setCourseDuration(courseRequest.getCourseDuration());
                    course.setCoursePayment(courseRequest.getCoursePayment());
                    course.setName(courseRequest.getName());
                    course.setLessonsCount(courseRequest.getLessonsCount());
                    course.setCost(courseRequest.getCost());
                    course.setImage(courseRequest.getImage());
                    return courseRepository.save(course);
                }).orElseThrow(() -> new ResourceNotFoundException("Course not found with id " + id));
    }

    @DeleteMapping("/coursetype/t{coursetypeId}/courseduration/d{coursedurationId}/coursepayment/p{coursepaymentId}/course/c{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long coursetypeId,
                                          @PathVariable Long coursedurationId,
                                          @PathVariable Long coursepaymentId,
                                          @PathVariable Long id) {
        if(!courseTypeRepository.existsById(coursetypeId)) {
            throw new ResourceNotFoundException("Course Type not found with id " + coursetypeId);
        }

        if(!courseDurationRepository.existsById(coursedurationId)) {
            throw new ResourceNotFoundException("Course Duration not found with id " + coursedurationId);
        }

        if(!coursePaymentRepository.existsById(coursepaymentId)) {
            throw new ResourceNotFoundException("Course Payment not found with id " + coursepaymentId);
        }

        return courseRepository.findById(id)
                .map(course -> {
                    courseRepository.delete(course);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Course not found with id " + id));

    }
}
