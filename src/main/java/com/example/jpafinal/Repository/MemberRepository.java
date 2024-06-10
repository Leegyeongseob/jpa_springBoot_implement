package com.example.jpafinal.Repository;

import com.example.jpafinal.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {
    Optional<Member> findByEmail(String email);
    boolean existsByEmail(String email);
    Optional<Member> findByEmailAndPwd(String email, String pwd);
}
