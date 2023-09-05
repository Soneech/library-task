package org.soneech.service;

import org.soneech.model.Book;
import org.soneech.model.Person;
import org.soneech.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Transactional
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Transactional
    public void update(int id, Book book) {
        book.setId(id);
        bookRepository.save(book);
    }

    @Transactional
    public void delete(int id) {
        bookRepository.deleteById(id);
    }

    public Optional<Book> findById(int id) {
        return bookRepository.findById(id);
    }

    @Transactional
    public void assignOwner(int bookId, Person owner) {
        Optional<Book> book = bookRepository.findById(bookId);
        if (book.isPresent()) {
            book.get().setOwner(owner);
        }
    }

    @Transactional
    public void releaseBook(int bookId) {
        Optional<Book> book = bookRepository.findById(bookId);
        if (book.isPresent()) {
            book.get().setOwner(null);
        }
    }

    public Optional<Book> findDuplicate(Book book) {
        return bookRepository.findByTitleAndAuthorAndYear(book.getTitle(), book.getAuthor(), book.getYear());
    }
}
