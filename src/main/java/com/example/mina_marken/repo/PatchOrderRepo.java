package com.example.mina_marken.repo;

import com.example.mina_marken.model.entity.PatchOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PatchOrderRepo extends JpaRepository<PatchOrder, Long> {

    List<PatchOrder> findByYearAndScoutGroup_Id(int year, Long scoutGroupId);

}
