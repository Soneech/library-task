package org.soneech.controller;

import jakarta.validation.Valid;
import org.soneech.model.Book;
import org.soneech.model.Person;
import org.soneech.service.BookService;
import org.soneech.service.PersonService;
import org.soneech.util.BookValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;
    private final PersonService personService;
    private final BookValidator bookValidator;

    @Autowired
    public BookController(BookService bookService, PersonService personService, BookValidator bookValidator) {
        this.bookService = bookService;
        this.personService = personService;
        this.bookValidator = bookValidator;
    }

    @GetMapping
    public String booksPage(Model model,
                            @RequestParam(required = false) Integer page,
                            @RequestParam(required = false, value = "books_per_page") Integer booksPerPage,
                            @RequestParam(required = false, value = "sort_by_year") boolean sortByYear) {
        List<Book> books;
        if (page == null || booksPerPage == null) {
            books = bookService.findAll(sortByYear);
        }
        else {
            books = bookService.findWithPagination(sortByYear, page, booksPerPage);
        }

        model.addAttribute("books", books);
        return "books/books_page";
    }

    @GetMapping("/new")
    public String creationPage(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    public void setErrorMessage(Model model) {
        model.addAttribute("error_message", "Нет такой книги!");
    }

    @GetMapping("/{id}")
    public String bookPage(@PathVariable("id") int id, Model model) {
        Optional<Book> book = bookService.findById(id);
        if (book.isPresent()) {
            model.addAttribute("book", book.get());
            if (book.get().getOwner() != null) {
                model.addAttribute("owner", book.get().getOwner());
            }
            else {
                model.addAttribute("people", personService.findAll());
                model.addAttribute("person", new Person());
            }
        }
        else {
            setErrorMessage(model);
        }
        return "books/show";
    }

    @GetMapping("/{id}/edit")
    public String editPage(@PathVariable("id") int id, Model model) {
        Optional<Book> book = bookService.findById(id);
        if (book.isPresent()) {
            model.addAttribute("book", book.get());
        }
        else {
            setErrorMessage(model);
        }

        return "books/edit";
    }

    @PostMapping
    public String createBook(@ModelAttribute("book") @Valid Book book,
                             BindingResult bindingResult, Model model) {
        bookValidator.validate(book, bindingResult);

        if (bindingResult.hasGlobalErrors()) {
            String globalError = bindingResult.getGlobalError().getDefaultMessage();
            model.addAttribute("globalError", globalError);
            return "books/new";
        }

        bookService.save(book);
        return "redirect:/books";
    }

    @PatchMapping("/{id}")
    public String editBook(@PathVariable("id") int id,
                           @ModelAttribute("book") @Valid Book book,
                           BindingResult bindingResult, Model model) {
        bookValidator.validate(book, bindingResult);

        if (bindingResult.hasErrors()) {
            if (bindingResult.hasGlobalErrors()) {
                String globalError = bindingResult.getGlobalError().getDefaultMessage();
                model.addAttribute("globalError", globalError);
            }
            return "books/edit";
        }
        bookService.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") int id) {
        bookService.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/assign")
    public String assignOwner(@PathVariable("id") int bookId, @ModelAttribute("person") Person person) {
        bookService.assignOwner(bookId, person);
        return "redirect:/books/" + bookId;
    }

    @PatchMapping("/{id}/release")
    public String releaseBook(@PathVariable("id") int id) {
        bookService.releaseBook(id);
        return "redirect:/books/" + id;
    }
}
