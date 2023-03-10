package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.model.Book;
import org.example.service.BookServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;
import java.util.*;


@RestController
@RequestMapping(path = "books")
@AllArgsConstructor
public class BookController {
    final String STATUS_MSG = "Status Msg";
    final String RESPONSE_OBJ = "Response Object";
    final BookServiceImpl bookService;
    final Logger logger = LoggerFactory.getLogger(BookController.class);

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks(@RequestParam(value = "bookId", required = false) String bookId){
        try{
            if(!ObjectUtils.isEmpty(bookId)){
                Optional<Book> queriedBook = bookService.findById(bookId);
                return queriedBook.map(book -> new ResponseEntity<>(List.of(book), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
            }
            List<Book> books = new ArrayList<>(bookService.getAllBooks());
            if(books.isEmpty()) {
                logger.info("No Content!");
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(books, HttpStatus.OK);
        }catch(Exception e){
            logger.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>>  addBook(@RequestBody Book newBook){
        try{
            if(ObjectUtils.isEmpty(newBook))
                return new ResponseEntity<>(Map.of(STATUS_MSG,"Given Pay-load Object is Empty!"), HttpStatus.BAD_REQUEST);
            Book addedBook = bookService.addBook(newBook);
            return new ResponseEntity<>(Map.of(STATUS_MSG, "Book Added Successfully",RESPONSE_OBJ, addedBook), HttpStatus.OK);
        }catch(EntityExistsException e){
            logger.error(e.getMessage());
            return new ResponseEntity<>(Map.of(STATUS_MSG, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<Map<String, Object>> updateBook(@RequestBody Book bookWithChanges){
        try{
            if(ObjectUtils.isEmpty(bookWithChanges))
                return new ResponseEntity<>(Map.of(STATUS_MSG,"Given pay-load object is empty!"), HttpStatus.BAD_REQUEST);
            bookService.updateBook(bookWithChanges);
            return new ResponseEntity<>(Map.of(STATUS_MSG, "Book Updated Successfully",RESPONSE_OBJ, bookWithChanges), HttpStatus.OK);
        }catch(Exception e){
            logger.error(e.getMessage());
            return new ResponseEntity<>(Map.of(STATUS_MSG, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
