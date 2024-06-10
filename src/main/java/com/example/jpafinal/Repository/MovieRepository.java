package com.example.jpafinal.Repository;

import com.example.jpafinal.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie,Long>{
}
