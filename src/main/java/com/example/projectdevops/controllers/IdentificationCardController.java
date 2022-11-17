package com.example.projectdevops.controllers;

import com.example.projectdevops.models.Book;
import com.example.projectdevops.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/identification")
@CrossOrigin(origins = "*")
public class IdentificationCardController {
    @Autowired
    BookService bookService;

    @GetMapping("/all")
    public List<Book> getBooks()
    {
        List<Book> books = this.bookService.getAllBooks();
        return books;
    }

    @GetMapping("/{bookId}")
    public Book getBookById(@PathVariable("bookId") Long bookId)
    {
        Book book = this.bookService.getBookById(bookId);
        return book;
    }

    @PostMapping("/create/{authorId}")
    public Book createNewBook(@RequestBody Book newBook, @PathVariable("authorId") Long authorId)
    {
        Book book = this.bookService.createBook(newBook, authorId);
        return book;
    }
}
