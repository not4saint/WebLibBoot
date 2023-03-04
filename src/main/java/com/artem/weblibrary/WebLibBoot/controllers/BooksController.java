package com.artem.weblibrary.WebLibBoot.controllers;


import com.artem.weblibrary.WebLibBoot.models.Book;
import com.artem.weblibrary.WebLibBoot.models.Person;
import com.artem.weblibrary.WebLibBoot.services.BookService;
import com.artem.weblibrary.WebLibBoot.services.PersonService;
import com.artem.weblibrary.WebLibBoot.utils.BookValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final BookService bookService;
    private final PersonService personService;
    private final BookValidator bookValidator;

    @Autowired
    public BooksController(BookService bookService, BookValidator bookValidator, PersonService personService) {
        this.bookService = bookService;
        this.bookValidator = bookValidator;
        this.personService = personService;
    }

    @GetMapping()
    public String showAllBooks(@RequestParam(defaultValue = "-1") int page,
                               @RequestParam(name = "books_per_page", defaultValue = "0") int booksPerPage,
                               @RequestParam(name = "sort_by_year", defaultValue = "false") boolean sortByYear,
                               Model model) {
        model.addAttribute("books", bookService.selectAllBooks(page, booksPerPage, sortByYear));
        return "books/allBooks";
    }

    @GetMapping("/{id}")
    public String showBook(@PathVariable int id, Model model, @ModelAttribute Person person) {
        model.addAttribute("book", bookService.selectOneBook(id));

        Optional<Person> bookOwner = bookService.getBookOwner(id);
        if (bookOwner.isPresent())
            model.addAttribute("owner", bookOwner.get());
        else
            model.addAttribute("people", personService.selectAllPeople());

        return "books/show";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute Book book) {
        return "books/new";
    }

    @PostMapping()
    public String createBook(@ModelAttribute @Valid Book book, BindingResult bindingResult) {
        bookValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors())
            return "books/new";

        bookService.saveBook(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String editBook(@PathVariable int id, Model model) {
        model.addAttribute("book", bookService.selectOneBook(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String updateBook(@PathVariable int id, @ModelAttribute @Valid Book book, BindingResult bindingResult) {
        bookValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors())
            return "books/edit";

        bookService.updateBook(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable int id) {
        bookService.deleteBookById(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable int id) {
        bookService.releaseBook(id);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable int id, @ModelAttribute Person person) {
        bookService.assignBook(id, person);
        return "redirect:/books/" + id;
    }

    //сделать проверку на список (пустой или с книгами), а также на отсутсвие запроса в title
    @GetMapping("/search")
    public String search(@RequestParam(value = "title", required = false) String title, Model model) {
        if (title != null) {
            List<Book> books = bookService.searchForBooksByPartOfItsName(title);
            if (books.isEmpty())
                model.addAttribute("empty", "No books found");
            else
                model.addAttribute("books", books);
        }
        return "books/search";
    }
}

