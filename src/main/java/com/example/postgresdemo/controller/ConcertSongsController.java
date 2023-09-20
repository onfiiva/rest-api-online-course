package com.example.postgresdemo.controller;

import com.example.postgresdemo.exception.ResourceNotFoundException;
import com.example.postgresdemo.model.ConcertSongs;
import com.example.postgresdemo.repository.ConcertRepository;
import com.example.postgresdemo.repository.ConcertSongsRepository;
import com.example.postgresdemo.repository.StudentSongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ConcertSongsController {
    @Autowired
    private ConcertSongsRepository concertSongsRepository;

    @Autowired
    private ConcertRepository concertRepository;

    @Autowired
    private StudentSongRepository studentSongRepository;

    @GetMapping("/concert/c{concertId}/studentsong/s{studentsongId}/concertsongs")
    public List<ConcertSongs> getConcertSongsByConcertIdAndSongId(@PathVariable Long concertId,
                                                     @PathVariable Long studentsongId) {
        return concertSongsRepository.findByConcertIdAndStudentSongId(concertId, studentsongId);
    }

    @PostMapping("/concert/c{concertId}/studentsong/s{studentsongId}/concertsongs")
    public ConcertSongs addConcertSong(@PathVariable Long concertId,
                                  @PathVariable Long studentsongId,
                                  @Valid @RequestBody ConcertSongs concertSong) {
        return concertRepository.findById(concertId)
                .map(concert -> {
                    concertSong.setConcert(concert);
                    return studentSongRepository.findById(studentsongId)
                            .map(studentSong -> {
                                concertSong.setStudentSong(studentSong);
                                return concertSongsRepository.save(concertSong);
                            })
                            .orElseThrow(() -> new ResourceNotFoundException("Student Song not found with id " + studentsongId));
                })
                .orElseThrow(() -> new ResourceNotFoundException("Concert not found with id " + concertId));
    }

    @PutMapping("/concert/c{concertId}/studentsong/s{studentsongId}/concertsongs/cs{id}")
    public ConcertSongs updateConcertSong(@PathVariable Long id,
                               @PathVariable Long concertId,
                               @PathVariable Long studentsongId,
                               @Valid @RequestBody ConcertSongs concertSongsRequest) {
        if(!concertRepository.existsById(concertId)) {
            throw new ResourceNotFoundException("Concert not found with id " + concertId);
        }

        if(!studentSongRepository.existsById(studentsongId)) {
            throw new ResourceNotFoundException("Student song not found with id " + studentsongId);
        }

        return concertSongsRepository.findById(id)
                .map(concertSong -> {
                    concertSong.setStudentSong(concertSongsRequest.getStudentSong());
                    concertSong.setConcert(concertSongsRequest.getConcert());
                    return concertSongsRepository.save(concertSong);
                }).orElseThrow(() -> new ResourceNotFoundException("Concert Song not found with id " + id));
    }

    @DeleteMapping("/concert/c{concertId}/studentsong/s{studentsongId}/concertsongs/cs{id}")
    public ResponseEntity<?> deleteConcertSong(@PathVariable Long id,
                                          @PathVariable Long concertId,
                                          @PathVariable Long studentsongId) {
        if(!concertRepository.existsById(concertId)) {
            throw new ResourceNotFoundException("Concert not found with id " + concertId);
        }

        if(!studentSongRepository.existsById(studentsongId)) {
            throw new ResourceNotFoundException("Student song not found with id " + studentsongId);
        }

        return concertSongsRepository.findById(id)
                .map(concertSong -> {
                    concertSongsRepository.delete(concertSong);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Concert Song not found with id " + id));

    }
}
