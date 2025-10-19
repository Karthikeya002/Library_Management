package com.example.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Book;
import com.example.demo.model.BorrowRecord;
import com.example.demo.model.Member;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.BorrowRecordRepository;
import com.example.demo.repository.MemberRepository;
import com.example.demo.service.BorrowingService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BorrowingServiceImpl implements BorrowingService {

    @Autowired
    private BorrowRecordRepository borrowRecordRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Override
    @Transactional
    public BorrowRecord borrowBook(Long bookId, Long memberId) {
        try {
            // Validate inputs
            if (bookId == null) {
                throw new RuntimeException("Book ID is required");
            }
            if (memberId == null) {
                throw new RuntimeException("Member ID is required");
            }

            // Find book
            Optional<Book> bookOptional = bookRepository.findById(bookId);
            if (bookOptional.isEmpty()) {
                throw new RuntimeException("Book not found with id: " + bookId);
            }
            Book book = bookOptional.get();
        
            // Find member
            Optional<Member> memberOptional = memberRepository.findById(memberId);
            if (memberOptional.isEmpty()) {
                throw new RuntimeException("Member not found with id: " + memberId);
            }
            Member member = memberOptional.get();

            // Check if book is available
            if (!Boolean.TRUE.equals(book.getIsAvailable())) {
                throw new RuntimeException("Book is not available for borrowing: " + book.getTitle());
            }

            // Check if book is already borrowed (additional check)
            Optional<BorrowRecord> existingRecord = borrowRecordRepository.findByBookIdAndReturnDateIsNull(bookId);
            if (existingRecord.isPresent()) {
                throw new RuntimeException("Book is already borrowed: " + book.getTitle());
            }

            // Create borrow record
            BorrowRecord record = new BorrowRecord();
            record.setBook(book);
            record.setMember(member);
            record.setBorrowDate(LocalDateTime.now());
            record.setDueDate(LocalDateTime.now().plusWeeks(2)); // 2 weeks due date
            record.setReturnDate(null);
            record.setStatus("BORROWED");

            // Update book availability
            book.setIsAvailable(false);
            bookRepository.save(book);

            return borrowRecordRepository.save(record);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error borrowing book: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public BorrowRecord returnBook(Long recordId) {
        try {
            if (recordId == null) {
                throw new RuntimeException("Record ID is required");
            }

            Optional<BorrowRecord> recordOptional = borrowRecordRepository.findById(recordId);
            if (recordOptional.isEmpty()) {
                throw new RuntimeException("Borrow record not found with id: " + recordId);
            }

            BorrowRecord record = recordOptional.get();
            
            // Check if book is already returned
            if (record.getReturnDate() != null) {
                throw new RuntimeException("Book has already been returned");
            }

            record.setReturnDate(LocalDateTime.now());
            record.setStatus("RETURNED");

            // Update book availability
            Book book = record.getBook();
            book.setIsAvailable(true);
            bookRepository.save(book);

            return borrowRecordRepository.save(record);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error returning book: " + e.getMessage());
        }
    }

    @Override
    public List<BorrowRecord> getBorrowingHistory() {
        try {
            return borrowRecordRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving borrowing history: " + e.getMessage());
        }
    }

    @Override
    public List<BorrowRecord> getCurrentBorrows() {
        try {
            return borrowRecordRepository.findByStatus("BORROWED");
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving current borrows: " + e.getMessage());
        }
    }

    @Override
    public List<BorrowRecord> getMemberBorrowHistory(Long memberId) {
        try {
            if (memberId == null) {
                throw new RuntimeException("Member ID is required");
            }
            return borrowRecordRepository.findByMemberId(memberId);
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving member borrow history: " + e.getMessage());
        }
    }

    @Override
    public List<BorrowRecord> getOverdueBooks() {
        try {
            List<BorrowRecord> currentBorrows = borrowRecordRepository.findByStatus("BORROWED");
            LocalDateTime now = LocalDateTime.now();
            
            // Check for overdue books
            for (BorrowRecord record : currentBorrows) {
                if (record.getDueDate() != null && record.getDueDate().isBefore(now)) {
                    record.setStatus("OVERDUE");
                    borrowRecordRepository.save(record);
                }
            }
            
            return borrowRecordRepository.findByStatus("OVERDUE");
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving overdue books: " + e.getMessage());
        }
    }
}