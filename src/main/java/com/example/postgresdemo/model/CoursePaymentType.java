package com.example.postgresdemo.model;

import javax.persistence.*;

@Entity
@Table(name = "CoursePaymentType")
public class CoursePaymentType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public CoursePaymentType(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public CoursePaymentType() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
