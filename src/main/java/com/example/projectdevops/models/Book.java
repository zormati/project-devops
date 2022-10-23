package com.example.projectdevops.models;

import javax.persistence.*;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "genre")
    private String genre;

    @Column(name = "isAvailable")
    private Boolean isAvailable = false;

    @Column(name = "plot")
    private String plot;

    @Column(name = "shouldBeBackdOn")
    private String  shouldBeBackdOn;

    @ManyToOne
    @Column(name = "author_id")
    private Author author;

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public String getPlot() {
        return plot;
    }

    public String getShouldBeBackdOn() {
        return shouldBeBackdOn;
    }

    public Book(String title, String genre, String plot) {
        this.title = title;
        this.genre = genre;
        this.plot = plot;
    }

    public Book(String title, String genre) {
        this.title = title;
        this.genre = genre;
    }

    public Book() {

    }

}
