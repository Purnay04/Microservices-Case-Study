package org.example.service;

import lombok.AllArgsConstructor;
import org.example.model.Book;
import org.example.repo.BookRepo;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService{
    final BookRepo bookRepo;
    public List<Book> getAllBooks() {
        return bookRepo.findAll();
    }

    public Optional<Book> findById(String id) {
        return bookRepo.findById(id);
    }

    public Book addBook(Book book) throws EntityExistsException{
        if(ObjectUtils.isEmpty(book))
            return null;
        if(!ObjectUtils.isEmpty(bookRepo.findById(book.getId()))){
            throw new EntityExistsException("Given book id is occupied please give unique!");
        }
        bookRepo.save(book);
        return book;
    }

    public Book updateBook(Book bookWithChanges) throws EntityNotFoundException{
        if(bookRepo.findById(bookWithChanges.getId()).isEmpty())
            throw new EntityNotFoundException("Given book id is not present");
        bookRepo.save(bookWithChanges);
        return bookWithChanges;
    }
}
