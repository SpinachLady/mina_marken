package com.example.mina_marken.repo;

import com.example.mina_marken.model.entity.Patch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatchRepo extends JpaRepository<Patch, Long> {

    Patch getPatchById(Long id);
    Patch getPatchByName(String name);
    List<Patch> findAll();
}
