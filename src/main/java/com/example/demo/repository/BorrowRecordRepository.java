package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.BorrowRecord;

import java.util.List;
import java.util.Optional;

@Repository
public interface BorrowRecordRepository extends JpaRepository<BorrowRecord, Long> {
    List<BorrowRecord> findByMemberId(Long memberId);
    List<BorrowRecord> findByBookId(Long bookId);
    Optional<BorrowRecord> findByBookIdAndReturnDateIsNull(Long bookId);
    List<BorrowRecord> findByStatus(String status);
}