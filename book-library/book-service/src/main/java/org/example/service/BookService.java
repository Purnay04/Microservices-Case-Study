package org.example.service;
import org.example.model.Book;
import java.util.List;
import java.util.Optional;

public interface BookService {
    List<Book> getAllBooks();
    Optional<Book> findById(String id);
    Book addBook(Book book);
}
