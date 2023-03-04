package com.artem.weblibrary.WebLibBoot.utils;

import com.artem.weblibrary.WebLibBoot.models.Book;
import com.artem.weblibrary.WebLibBoot.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class BookValidator implements Validator {
    private final BookService bookService;

    @Autowired
    public BookValidator(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Book.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Book book = (Book) o;
        if (bookService.checkByUniquenessNameAndAuthorOfTheBook(book.getName(), book.getAuthor()).isPresent()) {
            errors.rejectValue("name", "", "Book is already registered");
        }
    }
}
