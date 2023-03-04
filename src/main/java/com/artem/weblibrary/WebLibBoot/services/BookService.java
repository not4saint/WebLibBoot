package com.artem.weblibrary.WebLibBoot.services;


import com.artem.weblibrary.WebLibBoot.models.Book;
import com.artem.weblibrary.WebLibBoot.models.Person;
import com.artem.weblibrary.WebLibBoot.repositories.BooksRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
@Service
@Transactional(readOnly = true)
public class BookService {
    private final BooksRepository booksRepository;

    @Autowired
    public BookService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Book> selectAllBooks(int page, int size, boolean sort) {
        if (sort) {
            if (page >= 0 && size > 0)
                return booksRepository.findAll(PageRequest.of(page, size, Sort.by("date"))).getContent();
            else
                return booksRepository.findAll(Sort.by("date"));
        } else if (page >= 0 && size > 0)
            return booksRepository.findAll(PageRequest.of(page, size)).getContent();
        else
            return booksRepository.findAll();
    }

    public Book selectOneBook(int id) {
        Optional<Book> optionalBook = booksRepository.findById(id);
        return optionalBook.orElse(null);
    }

    @Transactional
    public void saveBook(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void updateBook(int id, Book book) {
        book.setId(id);
        booksRepository.save(book);
    }

    @Transactional
    public void deleteBookById(int id) {
        booksRepository.deleteById(id);
    }

    public Optional<Person> getBookOwner(int id) {
        Optional<Book> optionalBook = booksRepository.findById(id);

        if (optionalBook.isPresent()) {
            Hibernate.initialize(Optional.ofNullable(optionalBook.get().getOwner()));
            return Optional.ofNullable(optionalBook.get().getOwner());
        } else {
            return Optional.empty();
        }
    }

    @Transactional
    public void releaseBook(int id) {
        Optional<Book> optionalBook = booksRepository.findById(id);

        optionalBook.ifPresent(book -> {
            book.setCreatedAt(null);
            book.setOwner(null);
        });
    }

    @Transactional
    public void assignBook(int id, Person person) {
        Optional<Book> optionalBook = booksRepository.findById(id);

        optionalBook.ifPresent(book -> {
            book.setCreatedAt(new Date());
            book.setOwner(person);
        });
    }

    public Optional<Book> checkByUniquenessNameAndAuthorOfTheBook(String name, String author) {
        return booksRepository.findByNameAndAuthor(name, author).stream().findAny();
    }

    public List<Book> searchForBooksByPartOfItsName(String partOfName) {
        Optional<List<Book>> optionalBooks = Optional.of(booksRepository.findByNameStartingWith(partOfName));

        return optionalBooks.orElse(Collections.emptyList());
    }
}
