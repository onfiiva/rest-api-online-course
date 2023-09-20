package com.example.postgresdemo.controller;

import com.example.postgresdemo.exception.ResourceNotFoundException;
import com.example.postgresdemo.model.UserRoles;
import com.example.postgresdemo.repository.UserRolesRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UserRoleController {
    @Autowired
    private UserRolesRepository userRolesRepository;

    @GetMapping("/userroles")
    public List<UserRoles> getUserRoles() {
        return userRolesRepository.findAll();
    }


    @PostMapping("/userroles")
    public UserRoles createUserRole(@Valid @RequestBody UserRoles roleEnum) {
        return userRolesRepository.save(roleEnum);
    }

    @PutMapping("/userroles/{id}")
    public UserRoles updateUserRole(@PathVariable Long id,
                                             @Valid @RequestBody UserRoles userRolesRequest) {
        return userRolesRepository.findById(id)
                .map(userRole -> {
                    userRole.setName(userRolesRequest.getName());
                    return userRolesRepository.save(userRole);
                }).orElseThrow(() -> new ResourceNotFoundException("User Role not found with id " + id));
    }


    @DeleteMapping("/userroles/{id}")
    public ResponseEntity<?> deleteUserRole(@PathVariable Long id) {
        return userRolesRepository.findById(id)
                .map(userRole -> {
                    userRolesRepository.delete(userRole);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("User Role not found with id " + id));
    }
}
