package com.example.casaapp.repository;

import com.example.casaapp.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemeberRepository extends JpaRepository<Member, Long> {
    Member findByMemberid(String memberid);
}
