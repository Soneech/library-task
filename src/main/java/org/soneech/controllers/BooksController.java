package org.soneech.controllers;

import jakarta.validation.Valid;
import org.soneech.dao.BookDao;
import org.soneech.models.Book;
import org.soneech.util.BookValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final BookDao bookDao;
    private final BookValidator bookValidator;

    @Autowired
    public BooksController(BookDao bookDao, BookValidator bookValidator) {
        this.bookDao = bookDao;
        this.bookValidator = bookValidator;
    }

    @GetMapping
    public String booksPage(Model model) {
        model.addAttribute("books", bookDao.findAll());
        return "books/books_page";
    }

    @GetMapping("/new")
    public String creationPage(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @GetMapping("/{id}")
    public String bookPage(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookDao.findById(id));
        return "books/show";
    }

    @GetMapping("/{id}/edit")
    public String editPage(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookDao.findById(id));
        return "books/edit";
    }

    @PostMapping
    public String createBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult, Model model) {
        bookValidator.validate(book, bindingResult);

        if (bindingResult.hasErrors()) {
            if (bindingResult.hasGlobalErrors()) {
                String globalError = bindingResult.getGlobalError().getDefaultMessage();
                model.addAttribute("globalError", globalError);
            }
            return "books/new";
        }

        bookDao.save(book);
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
        bookDao.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") int id) {
        bookDao.delete(id);
        return "redirect:/books";
    }
}
