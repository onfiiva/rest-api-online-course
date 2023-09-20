package com.example.postgresdemo.controller;

import com.example.postgresdemo.exception.ResourceNotFoundException;
import com.example.postgresdemo.model.Concert;
import com.example.postgresdemo.repository.ConcertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class ConcertController {

    @Autowired
    private ConcertRepository concertRepository;

    @GetMapping("/concerts")
    public Page<Concert> getConcerts(Pageable pageable) {
        return concertRepository.findAll(pageable);
    }


    @PostMapping("/concerts")
    public Concert createConcert(@Valid @RequestBody Concert concert) {
        return concertRepository.save(concert);
    }

    @PutMapping("/concerts/{id}")
    public Concert updateConcert(@PathVariable Long id,
                                   @Valid @RequestBody Concert concertRequest) {
        return concertRepository.findById(id)
                .map(concert -> {
                    concert.setDate(concertRequest.getDate());
                    return concertRepository.save(concert);
                }).orElseThrow(() -> new ResourceNotFoundException("Concert not found with id " + id));
    }


    @DeleteMapping("/concerts/{id}")
    public ResponseEntity<?> deleteConcert(@PathVariable Long id) {
        return concertRepository.findById(id)
                .map(concert -> {
                    concertRepository.delete(concert);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Concert not found with id " + id));
    }
}

