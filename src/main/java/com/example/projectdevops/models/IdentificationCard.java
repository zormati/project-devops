package com.example.projectdevops.models;

import javax.persistence.*;
@Entity
public class IdentificationCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "schoolYear")
    private String schoolYear;

    @OneToOne(mappedBy = "identificationCard")
    private Student student;

    public IdentificationCard(String schoolYear) {
        this.schoolYear = schoolYear;
    }

    public IdentificationCard() {
    }
}
