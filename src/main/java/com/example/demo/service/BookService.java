package com.example.demo.service;
import java.util.List;

import com.example.demo.model.Book;
import java.util.List;

public interface BookService {
    List<Book> getAllBooks();
    Book getBookById(Long id);
    Book createBook(Book book);
    Book updateBook(Long id, Book book);
    boolean deleteBook(Long id);
    List<Book> searchBooks(String keyword);
    List<Book> getBooksByReadStatus(Boolean isRead);
    List<Book> getBooksByAuthor(String author);
    List<Book> getBooksByGenre(String genre);
    Book markAsRead(Long id);
    Book markAsUnread(Long id);
}