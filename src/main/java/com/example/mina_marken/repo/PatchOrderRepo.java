package com.example.mina_marken.repo;

import com.example.mina_marken.model.entity.Patch;
import com.example.mina_marken.model.entity.PatchOrder;
import com.example.mina_marken.model.entity.ScoutGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PatchOrderRepo extends JpaRepository<PatchOrder, Long> {

    List<PatchOrder> findByYearAndScoutGroup(int year, ScoutGroup scoutGroup);

    List<PatchOrder> findByPatch(Patch patch);
}
