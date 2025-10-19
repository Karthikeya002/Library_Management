package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Member;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByFirstNameContainingIgnoreCase(String firstName);
    List<Member> findByLastNameContainingIgnoreCase(String lastName);
    boolean existsByEmail(String email);
}