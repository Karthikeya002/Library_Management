package com.example.demo.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Member;
import com.example.demo.repository.MemberRepository;
import com.example.demo.service.MemberService;

import java.util.List;
import java.util.Optional;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public List<Member> getAllMembers() {
        try {
            return memberRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving members: " + e.getMessage());
        }
    }

    @Override
    public Member getMemberById(Long id) {
        try {
            Optional<Member> member = memberRepository.findById(id);
            if (member.isPresent()) {
                return member.get();
            } else {
                throw new RuntimeException("Member not found with id: " + id);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving member with id " + id + ": " + e.getMessage());
        }
    }

    @Override
    public Member createMember(Member member) {
        try {
            // Validate required fields
            if (member.getFirstName() == null || member.getFirstName().trim().isEmpty()) {
                throw new RuntimeException("First name is required");
            }
            if (member.getLastName() == null || member.getLastName().trim().isEmpty()) {
                throw new RuntimeException("Last name is required");
            }
            if (member.getEmail() == null || member.getEmail().trim().isEmpty()) {
                throw new RuntimeException("Email is required");
            }

            // Validate email format
            if (!isValidEmail(member.getEmail())) {
                throw new RuntimeException("Invalid email format");
            }

            // Check email uniqueness
            if (memberRepository.existsByEmail(member.getEmail())) {
                throw new RuntimeException("Email already exists: " + member.getEmail());
            }

            return memberRepository.save(member);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error creating member: " + e.getMessage());
        }
    }

    @Override
    public Member updateMember(Long id, Member memberDetails) {
        try {
            Member existingMember = getMemberById(id);
            
            // Update fields if they are provided
            if (memberDetails.getFirstName() != null) {
                existingMember.setFirstName(memberDetails.getFirstName());
            }
            if (memberDetails.getLastName() != null) {
                existingMember.setLastName(memberDetails.getLastName());
            }
            if (memberDetails.getEmail() != null) {
                // Validate email
                if (!isValidEmail(memberDetails.getEmail())) {
                    throw new RuntimeException("Invalid email format");
                }
                // Check email uniqueness if it's being changed
                if (!memberDetails.getEmail().equals(existingMember.getEmail()) && 
                    memberRepository.existsByEmail(memberDetails.getEmail())) {
                    throw new RuntimeException("Email already exists: " + memberDetails.getEmail());
                }
                existingMember.setEmail(memberDetails.getEmail());
            }
            if (memberDetails.getPhone() != null) {
                existingMember.setPhone(memberDetails.getPhone());
            }

            return memberRepository.save(existingMember);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error updating member with id " + id + ": " + e.getMessage());
        }
    }

    @Override
    public void deleteMember(Long id) {
        try {
            if (!memberRepository.existsById(id)) {
                throw new RuntimeException("Member not found with id: " + id);
            }
            memberRepository.deleteById(id);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error deleting member with id " + id + ": " + e.getMessage());
        }
    }

    @Override
    public List<Member> searchMembers(String keyword) {
        try {
            if (keyword == null || keyword.trim().isEmpty()) {
                return memberRepository.findAll();
            }
            return memberRepository.findByFirstNameContainingIgnoreCase(keyword);
        } catch (Exception e) {
            throw new RuntimeException("Error searching members: " + e.getMessage());
        }
    }

    @Override
    public boolean existsByEmail(String email) {
        try {
            return memberRepository.existsByEmail(email);
        } catch (Exception e) {
            throw new RuntimeException("Error checking email existence: " + e.getMessage());
        }
    }

    // Helper method to validate email format
    private boolean isValidEmail(String email) {
        if (email == null) return false;
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email.matches(emailRegex);
    }
}