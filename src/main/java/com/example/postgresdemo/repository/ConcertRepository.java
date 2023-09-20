package com.example.postgresdemo.repository;

import com.example.postgresdemo.model.Concert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcertRepository extends JpaRepository<Concert,Long> {
}
