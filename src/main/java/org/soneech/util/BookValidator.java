package org.soneech.util;

import org.soneech.model.Book;
import org.soneech.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class BookValidator implements Validator {
    private final BookService bookService;

    @Autowired
    public BookValidator(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;
        Optional<Book> foundBook = bookService.findDuplicate(book);
        if (foundBook.isPresent() && foundBook.get().getId() != book.getId()) {
            errors.reject("500", "Такая книга уже добавлена");
        }
    }
}
