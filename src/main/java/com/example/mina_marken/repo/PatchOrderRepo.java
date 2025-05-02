package com.example.mina_marken.repo;

import com.example.mina_marken.model.Term;
import com.example.mina_marken.model.entity.Patch;
import com.example.mina_marken.model.entity.PatchOrder;
import com.example.mina_marken.model.entity.ScoutGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface PatchOrderRepo extends JpaRepository<PatchOrder, Long> {

    @Query("SELECT po FROM PatchOrder po WHERE po.isArchived = false")
    List<PatchOrder> findAllActive();

    @Query("SELECT po FROM PatchOrder po WHERE po.isArchived = true")
    List<PatchOrder> findAllArchived();

    @Query("SELECT po FROM PatchOrder po WHERE po.year = :year AND po.term = :term AND po.scoutGroup = :scoutGroup AND po.isArchived = false")
    List<PatchOrder> findActiveByYearAndTermAndScoutGroup(@Param("year") int year, @Param("term") Term term, @Param("scoutGroup") ScoutGroup scoutGroup);

    @Query("SELECT po FROM PatchOrder po WHERE po.patch = :patch AND po.isArchived = false")
    List<PatchOrder> findActiveByPatch(@Param("patch") Patch patch);

    @Query("""
    SELECT p FROM PatchOrder p
    WHERE (:scoutGroup IS NULL OR p.scoutGroup = :scoutGroup)
    AND (:term IS NULL OR p.term = :term)
    AND (:year IS NULL OR p.year = :year)
    AND (:patch IS NULL OR p.patch = :patch)
    AND (p.isArchived = false)
""")
    List<PatchOrder> findAllActiveByAdvancedSearch(@Param("patch") Patch patch, @Param("scoutGroup") ScoutGroup scoutGroup, @Param("term") Term term, @Param("year") Integer year);

}
