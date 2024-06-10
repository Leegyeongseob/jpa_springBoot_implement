package com.example.jpafinal.Repository;

import com.example.jpafinal.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location,Long> {
}
