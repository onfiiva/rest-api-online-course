package com.example.postgresdemo.repository;

import com.example.postgresdemo.model.CourseDuration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseDurationRepository extends JpaRepository<CourseDuration, Long> {
}
