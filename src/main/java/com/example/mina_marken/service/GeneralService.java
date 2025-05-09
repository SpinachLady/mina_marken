package com.example.mina_marken.service;

import com.example.mina_marken.model.Term;
import com.example.mina_marken.model.entity.ScoutGroup;

import java.util.List;

public interface GeneralService {
    List<Integer> getActiveBirthYears();
    List<Integer> getActiveStartYears();
    List<Integer> getYearsBetween(int latestYear, int earliestYear);
    List<Integer> getBirthYearsFromScoutGroupAndYearAndTerm(ScoutGroup scoutGroup, int year, Term term);

    Term getCurrentTerm();
}
