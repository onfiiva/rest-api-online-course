package com.example.postgresdemo.repository;

import com.example.postgresdemo.model.modelUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<modelUser,Long> {
    List<modelUser> findByRoleIdAndCourseId(Long roleId, Long courseId);

    modelUser findByUsername(String username);
}
