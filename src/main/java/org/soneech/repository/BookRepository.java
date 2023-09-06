package org.soneech.repository;

import org.soneech.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    Optional<Book> findByTitleAndAuthorAndYear(String title, String author, int year);
    List<Book> findByTitleStartingWith(String title);
}
