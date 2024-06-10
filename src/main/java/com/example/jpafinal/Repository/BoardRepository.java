package com.example.jpafinal.Repository;

import com.example.jpafinal.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board,Long> {
    List<Board> findByTitleContaining(String keyword);
    List<Board> findByMemberEmail(String email);
    //페이징 기법을 위한 메소드 정의
    Page<Board> findAll(Pageable pageable);
}
