package com.example.postgresdemo.controller;

import com.example.postgresdemo.exception.ResourceNotFoundException;
import com.example.postgresdemo.model.CourseDuration;
import com.example.postgresdemo.repository.CourseDurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class CourseDurationController {
    @Autowired
    private CourseDurationRepository courseDurationRepository;

    @GetMapping("/coursedurations")
    public Page<CourseDuration> getCourseDurations(Pageable pageable) {
        return courseDurationRepository.findAll(pageable);
    }


    @PostMapping("/coursedurations")
    public CourseDuration createCourseDuration(@Valid @RequestBody CourseDuration courseDuration) {
        return courseDurationRepository.save(courseDuration);
    }

    @PutMapping("/coursedurations/{id}")
    public CourseDuration updateCourseDuration(@PathVariable Long id,
                                 @Valid @RequestBody CourseDuration courseDurationRequest) {
        return courseDurationRepository.findById(id)
                .map(courseDuration -> {
                    courseDuration.setDuration(courseDurationRequest.getDuration());
                    return courseDurationRepository.save(courseDuration);
                }).orElseThrow(() -> new ResourceNotFoundException("Course Duration not found with id " + id));
    }


    @DeleteMapping("/coursedurations/{id}")
    public ResponseEntity<?> deleteCourseDuration(@PathVariable Long id) {
        return courseDurationRepository.findById(id)
                .map(courseDuration -> {
                    courseDurationRepository.delete(courseDuration);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Course Duration not found with id " + id));
    }
}
