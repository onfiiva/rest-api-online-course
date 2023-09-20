package com.example.postgresdemo.controller;

import com.example.postgresdemo.exception.ResourceNotFoundException;
import com.example.postgresdemo.model.modelUser;
import com.example.postgresdemo.repository.CourseRepository;
import com.example.postgresdemo.repository.UserRepository;
import com.example.postgresdemo.repository.UserRolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ModelUserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRolesRepository userRolesRepository;

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping("/userrole/{roleId}/course/{courseId}/users")
    public List<modelUser> getUsersByUserRoleIdAndCourseId(@PathVariable Long roleId,
                                                               @PathVariable Long courseId) {
        return userRepository.findByRoleIdAndCourseId(roleId, courseId);
    }

    @GetMapping("/u{username}/user")
    public modelUser getUserByUsername(@PathVariable String username) {
        return userRepository.findByUsername(username);
    }

    @PostMapping("/userrole/{roleId}/course/{courseId}/users")
    public modelUser addUser(@PathVariable Long roleId,
                                @PathVariable Long courseId,
                                @Valid @RequestBody modelUser user) {
        return userRolesRepository.findById(roleId)
                .map(userRole -> {
                    user.setRole(userRole);
                    return courseRepository.findById(courseId)
                            .map(course -> {
                                user.setCourse(course);
                                return userRepository.save(user);
                            })
                            .orElseThrow(() -> new ResourceNotFoundException("Course not found with id " + courseId));
                })
                .orElseThrow(() -> new ResourceNotFoundException("User Role not found with id " + roleId));
    }

    @PostMapping("/userrole/{roleId}/users")
    public modelUser addUserWithoutCourse(@PathVariable Long roleId,
                             @Valid @RequestBody modelUser user) {
        return userRolesRepository.findById(roleId)
                .map(userRole -> {
                    user.setRole(userRole);
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new ResourceNotFoundException("User Role not found with id " + roleId));
    }

    @PutMapping("/userrole/{roleId}/course/{courseId}/users/{id}")
    public modelUser updateUser(@PathVariable Long roleId,
                                          @PathVariable Long courseId,
                                          @PathVariable Long id,
                                          @Valid @RequestBody modelUser userRequest) {

        if(!userRolesRepository.existsById(roleId)) {
            throw new ResourceNotFoundException("User Role not found with id " + roleId);
        }

        if(!courseRepository.existsById(courseId)) {
            throw new ResourceNotFoundException("Course not found with id " + courseId);
        }

        return userRepository.findById(id)
                .map(user -> {
                    user.setRole(userRequest.getRole());
                    user.setCourse(userRequest.getCourse());
                    return userRepository.save(user);
                }).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
    }

    @DeleteMapping("/userrole/{roleId}/course/{courseId}/users/{id}")
    public ResponseEntity<?> deleteConcertSong(@PathVariable Long roleId,
                                               @PathVariable Long courseId,
                                               @PathVariable Long id) {
        if(!userRolesRepository.existsById(roleId)) {
            throw new ResourceNotFoundException("User Role not found with id " + roleId);
        }

        if(!courseRepository.existsById(courseId)) {
            throw new ResourceNotFoundException("Course not found with id " + courseId);
        }

        return userRepository.findById(id)
                .map(user -> {
                    userRepository.delete(user);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));

    }
}
