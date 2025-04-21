package com.example.mina_marken.repo;

import com.example.mina_marken.model.entity.Patch;
import com.example.mina_marken.model.entity.ScoutGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ScoutGroupRepo extends JpaRepository<ScoutGroup, Long> {

    @Query("SELECT s FROM ScoutGroup s WHERE :age BETWEEN s.minAge AND s.maxAge")
    ScoutGroup findByAgeInRange(@Param("age") int age);

}
