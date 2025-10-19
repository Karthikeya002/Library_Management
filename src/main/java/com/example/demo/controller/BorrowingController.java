package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.BorrowRecord;
import com.example.demo.service.BorrowingService;

import java.util.List;

@RestController
@RequestMapping("/api/borrowing")
public class BorrowingController {

    @Autowired
    private BorrowingService borrowingService;

    @PostMapping("/borrow")
    public ResponseEntity<BorrowRecord> borrowBook(
            @RequestParam Long bookId,
            @RequestParam Long memberId) {
        try {
            BorrowRecord record = borrowingService.borrowBook(bookId, memberId);
            return ResponseEntity.ok(record);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/return/{recordId}")
    public ResponseEntity<BorrowRecord> returnBook(@PathVariable Long recordId) {
        try {
            BorrowRecord record = borrowingService.returnBook(recordId);
            return ResponseEntity.ok(record);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/history")
    public List<BorrowRecord> getBorrowingHistory() {
        return borrowingService.getBorrowingHistory();
    }

    @GetMapping("/current")
    public List<BorrowRecord> getCurrentBorrows() {
        return borrowingService.getCurrentBorrows();
    }

    @GetMapping("/member/{memberId}")
    public List<BorrowRecord> getMemberBorrowHistory(@PathVariable Long memberId) {
        return borrowingService.getMemberBorrowHistory(memberId);
    }

    @GetMapping("/overdue")
    public List<BorrowRecord> getOverdueBooks() {
        return borrowingService.getOverdueBooks();
    }
}