package com.example.projectdevops.services;

import com.example.projectdevops.exceptions.ResourceNotFoundException;
import com.example.projectdevops.models.Book;
import com.example.projectdevops.models.Author;
import com.example.projectdevops.repositories.AuthorRepository;
import com.example.projectdevops.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;

    public Book createBook(Book book, Long authorId) throws ResourceNotFoundException {

        Optional<Author> authorData = this.authorRepository.findById(authorId);
        if(authorData.isPresent()) {
            Author author = authorData.orElseThrow(()-> new ResourceNotFoundException("Book not found"));
            book.setAuthor(author);
            Book createdBook = this.bookRepository.save(book);
            return createdBook;
        }else{
        throw new  ResourceNotFoundException("No matching author, therefore can't create book.");
    }

    }

    public List<Book> getAllBooks() throws ResourceNotFoundException {

        List<Book> books = this.bookRepository.findAll();
        return books;
    }

    public Book getBookById(Long bookId) throws ResourceNotFoundException {

        Optional<Book> bookData = this.bookRepository.findById(bookId);
        if(bookData.isPresent()){
            Book book = bookData.orElseThrow(()-> new ResourceNotFoundException("book not found"));
            return book;
        }else{
            throw new  ResourceNotFoundException("Not book matches.");
        }
    }
}
