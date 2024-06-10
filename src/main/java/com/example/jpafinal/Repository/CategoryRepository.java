package com.example.jpafinal.Repository;

import com.example.jpafinal.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    List<Category> findByMemberEmail(String email);
}
