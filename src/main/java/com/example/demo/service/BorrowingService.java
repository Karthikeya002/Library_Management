package com.example.demo.service;

import java.util.List;

import com.example.demo.model.BorrowRecord;

public interface BorrowingService {
    BorrowRecord borrowBook(Long bookId, Long memberId);
    BorrowRecord returnBook(Long recordId);
    List<BorrowRecord> getBorrowingHistory();
    List<BorrowRecord> getCurrentBorrows();
    List<BorrowRecord> getMemberBorrowHistory(Long memberId);
    List<BorrowRecord> getOverdueBooks();
}