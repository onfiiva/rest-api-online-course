package com.example.postgresdemo.controller;

import com.example.postgresdemo.exception.ResourceNotFoundException;
import com.example.postgresdemo.model.StudentSong;
import com.example.postgresdemo.repository.StudentSongRepository;
import com.example.postgresdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class StudentSongController {
    @Autowired
    private StudentSongRepository studentSongRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users/{userId}/studentsongs")
    public List<StudentSong> getStudentSongsByUserId(@PathVariable Long userId) {
        return studentSongRepository.findByUserId(userId);
    }


    @PostMapping("/users/{userId}/studentsongs")
    public StudentSong createStudentSong(@PathVariable Long userId,
                                             @Valid @RequestBody StudentSong studentSong) {
        return userRepository.findById(userId)
                .map(user -> {
                    studentSong.setUser(user);
                    return studentSongRepository.save(studentSong);
                }).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
    }

    @PutMapping("/users/{userId}/studentsongs/{id}")
    public StudentSong updateStudentSong(@PathVariable Long userId,
                                             @PathVariable Long id,
                                             @Valid @RequestBody StudentSong studentSongRequest) {
        if(!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id " + userId);
        }

        return studentSongRepository.findById(id)
                .map(studentSong -> {
                    studentSong.setUser(studentSongRequest.getUser());
                    return studentSongRepository.save(studentSong);
                }).orElseThrow(() -> new ResourceNotFoundException("Student Song not found with id " + id));
    }


    @DeleteMapping("/users/{userId}/studentsongs/{id}")
    public ResponseEntity<?> deleteStudentSong(@PathVariable Long userId,
                                                 @PathVariable Long id) {
        if(!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id " + userId);
        }

        return studentSongRepository.findById(id)
                .map(studentSong -> {
                    studentSongRepository.delete(studentSong);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Student Song not found with id " + id));
    }
}
