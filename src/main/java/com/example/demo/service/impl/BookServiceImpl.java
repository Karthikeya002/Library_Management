package com.example.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Book;
import com.example.demo.repository.BookRepository;
import com.example.demo.service.BookService;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBookById(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            return book.get();
        } else {
            throw new RuntimeException("Book not found with id: " + id);
        }
    }

    @Override
    public Book createBook(Book book) {
        // Validate ISBN uniqueness if provided
        if (book.getIsbn() != null && !book.getIsbn().isEmpty()) {
            if (bookRepository.existsByIsbn(book.getIsbn())) {
                throw new RuntimeException("ISBN already exists: " + book.getIsbn());
            }
        }
        return bookRepository.save(book);
    }

    @Override
    public Book updateBook(Long id, Book bookDetails) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            
            // Update fields - FIXED: Using proper setter methods
            if (bookDetails.getTitle() != null) {
                book.setTitle(bookDetails.getTitle());
            }
            if (bookDetails.getAuthor() != null) {
                book.setAuthor(bookDetails.getAuthor());
            }
            if (bookDetails.getIsbn() != null) {
                // Check ISBN uniqueness if it's being changed
                if (!bookDetails.getIsbn().equals(book.getIsbn()) &&
                    bookRepository.existsByIsbn(bookDetails.getIsbn())) {
                    throw new RuntimeException("ISBN already exists: " + bookDetails.getIsbn());
                }
                book.setIsbn(bookDetails.getIsbn());
            }
            if (bookDetails.getGenre() != null) {
                book.setGenre(bookDetails.getGenre());
            }
            if (bookDetails.getPublicationYear() != null) {
                book.setPublicationYear(bookDetails.getPublicationYear());
            }
            if (bookDetails.getIsAvailable() != null) {
                book.setIsAvailable(bookDetails.getIsAvailable());
            }
            
            return bookRepository.save(book);
        }
        throw new RuntimeException("Book not found with id: " + id);
    }

    @Override
    public boolean deleteBook(Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Book> searchBooks(String keyword) {
        return bookRepository.searchByKeyword(keyword);
    }

    @Override
    public List<Book> getBooksByReadStatus(Boolean isRead) {
        // Note: Changed from isRead to isAvailable to match our entity
        return bookRepository.findByIsAvailable(isRead);
    }

    @Override
    public List<Book> getBooksByAuthor(String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(author);
    }

    @Override
    public List<Book> getBooksByGenre(String genre) {
        return bookRepository.findByGenreContainingIgnoreCase(genre);
    }

    @Override
    public Book markAsRead(Long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            book.setIsAvailable(false); // Mark as borrowed/read
            return bookRepository.save(book);
        }
        throw new RuntimeException("Book not found with id: " + id);
    }

    @Override
    public Book markAsUnread(Long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            book.setIsAvailable(true); // Mark as available/unread
            return bookRepository.save(book);
        }
        throw new RuntimeException("Book not found with id: " + id);
    }
}