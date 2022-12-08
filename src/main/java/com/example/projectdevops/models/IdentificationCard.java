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

    public IdentificationCard(Long id,String schoolYear) {
        this.id = id;
        this.schoolYear = schoolYear;
    }
    public IdentificationCard() {
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getSchoolYear() {
        return this.schoolYear;
    }

    public void setSchoolYear(String schoolYear) {
        this.schoolYear = schoolYear;
    }

}
