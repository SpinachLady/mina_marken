package com.example.mina_marken.service;

import com.example.mina_marken.model.Term;
import com.example.mina_marken.model.entity.ScoutGroup;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@Service
public class GeneralServiceImpl implements GeneralService {

    public List<Integer> getActiveBirthYears() {
        int currentYearValue = Year.now().getValue();
        int earliestYear = 2010;
        int latestYear = currentYearValue - 5;

        return getYearsBetween(latestYear, earliestYear);
    }
    public List<Integer> getActiveStartYears() {
        int currentYearValue = Year.now().getValue();
        int earliestYear = 2018;

        return getYearsBetween(currentYearValue, earliestYear);
    }

    public List<Integer> getYearsBetween(int latestYear, int earliestYear) {
        List<Integer> years = new ArrayList<>();
        while (earliestYear <= latestYear) {
            years.add(earliestYear);
            earliestYear++;
        }
        return years;
    }

    public List<Integer> getBirthYearsFromScoutGroupAndYearAndTerm(ScoutGroup scoutGroup, int year, Term term) {
        int minBirthYear;
        int maxBirthYear;
        if (term.equals(Term.HT)) {
            minBirthYear = year - scoutGroup.getMinAge();
            maxBirthYear = year - scoutGroup.getMaxAge();
        }
        else {
            minBirthYear = (year - scoutGroup.getMinAge()) + 1;
            maxBirthYear = (year - scoutGroup.getMaxAge()) + 1;
        }

        return getYearsBetween(minBirthYear, maxBirthYear);
    }
}
