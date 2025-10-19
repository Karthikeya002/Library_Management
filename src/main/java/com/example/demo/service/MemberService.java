package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Member;

public interface MemberService {
    List<Member> getAllMembers();
    Member getMemberById(Long id);
    Member createMember(Member member);
    Member updateMember(Long id, Member member);
    void deleteMember(Long id);
    List<Member> searchMembers(String keyword);
    boolean existsByEmail(String email);
}