package com.artem.weblibrary.WebLibBoot.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import java.util.List;

@Entity
@Table
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    @NotEmpty(message = "Name should be not empty")
    @Pattern(regexp = "[A-Z]\\w+\\s[A-Z]\\w+\\s[A-Z]\\w+", message = "The name should be in this format: LastName FirstName MiddleName")
    private String name;

    //здесь надо поиграть с датой + подключить норм столбец в дб
    @Max(value = 2023, message = "Date should be between 1900 and 2023")
    @Min(value = 1900, message = "Date should be between 1900 and 2023")
    private int date;
    @OneToMany(mappedBy = "owner")
    private List<Book> books;

    public Person(String name, int date) {
        this.name = name;
        this.date = date;
    }

    public Person() {
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

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
