package com.example.demo.repository;


import com.example.demo.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    
    // Search by title (case-insensitive, partial match)
    List<Book> findByTitleContainingIgnoreCase(String title);
    
    // Search by author (case-insensitive, partial match)
    List<Book> findByAuthorContainingIgnoreCase(String author);
    
    // Search by genre (case-insensitive, partial match)
    List<Book> findByGenreContainingIgnoreCase(String genre);
    
    // Search by ISBN (exact match)
    List<Book> findByIsbn(String isbn);
    
    // Find books by availability status
    List<Book> findByIsAvailable(Boolean isAvailable);
    
    // Find books by publication year range
    List<Book> findByPublicationYearBetween(Integer startYear, Integer endYear);
    
    // FIXED: Custom search query that searches in title, author, and genre
    @Query("SELECT b FROM Book b WHERE " +
           "LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(b.author) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(b.genre) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Book> searchByKeyword(@Param("keyword") String keyword);
    
    // Check if ISBN already exists (for validation)
    boolean existsByIsbn(String isbn);
}