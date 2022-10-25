package com.example.projectdevops.services;

import com.example.projectdevops.models.Author;
import com.example.projectdevops.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.projectdevops.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    @Autowired
    AuthorRepository authorRepository;

    public Author createAuthor(Author author) throws ResourceNotFoundException {

        Author createdAuthor = this.authorRepository.save(author);
        return createdAuthor;
    }

    public List<Author> getAllAuthors() throws ResourceNotFoundException {

        List<Author> authors = this.authorRepository.findAll();
        return authors;
    }

    public Author getAuthorById(Long authorId) throws ResourceNotFoundException {

        Optional<Author> authorData = this.authorRepository.findById(authorId);
        if(authorData.isPresent()){
            Author author = authorData.orElseThrow(()-> new ResourceNotFoundException("author not found"));
            return author;
        }else{
            throw new  ResourceNotFoundException("Not author matches.");
        }
    }
}
