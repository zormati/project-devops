package com.example.projectdevops.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "bornAt")
    private String bornAt;

    @Column(name = "cin")
    private String cin;

    @Column(name = "section")
    private String section;

    @Column(name = "year")
    private int year;

    @OneToMany(mappedBy = "student")
    private List<Book> books;

    @OneToOne()
    @JoinColumn(name = "identificationCard_id")
    private IdentificationCard identificationCard;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBornAt() {
        return bornAt;
    }

    public void setBornAt(String bornAt) {
        this.bornAt = bornAt;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Student(String firstName, String lastName, String bornAt, String cin) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.bornAt = bornAt;
        this.cin = cin;
    }

    public Student(Long id,String firstName, String lastName, String bornAt, String cin) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.bornAt = bornAt;
        this.cin = cin;
    }

    public Student() {
    }
}
