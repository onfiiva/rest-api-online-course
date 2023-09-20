package com.example.postgresdemo.repository;

import com.example.postgresdemo.model.StudentSong;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentSongRepository extends JpaRepository<StudentSong, Long> {
    List<StudentSong> findByUserId(Long userId);
}
