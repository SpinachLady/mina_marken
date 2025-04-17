package com.example.mina_marken.repo;

import com.example.mina_marken.model.entity.Patch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatchTypeRepo extends JpaRepository<Patch, Long> {

}
