package com.artem.weblibrary.WebLibBoot.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import java.util.Date;

@Entity
@Table
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    @NotEmpty(message = "Name should be not empty")
    private String name;
    @Column
    @Pattern(regexp = "[A-Z].[A-Z].[A-Z]\\w+", message = "Author's name should be in this format: A.S.Pushkin")
    private String author;
    @Column
    @Min(value = 0, message = "Book's date should be greater than 0")
    @Max(value = 2023, message = "Book's date should be less than 2023")
    private int date;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person owner;
    @Transient
    private boolean dept;

    public Book(String name, String author, int date) {
        this.name = name;
        this.author = author;
        this.date = date;
    }

    public Book() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isDept() {
        if (getCreatedAt() == null)
            return false;
        return (new Date().getTime() - getCreatedAt().getTime()) / 86_400_000 >= 10;
    }

    public void setDept(boolean dept) {
        this.dept = dept;
    }
}
