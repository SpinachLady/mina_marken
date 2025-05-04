package com.example.mina_marken.service;

import com.example.mina_marken.model.Term;
import com.example.mina_marken.model.entity.ScoutGroup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Year;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
class GeneralServiceImplTest {

    private GeneralServiceImpl generalService;

    @BeforeEach
    void setUp() {
        generalService = new GeneralServiceImpl();
    }

    @Test
    void testGetActiveBirthYears() {
        int expectedEarliestYear = 2010;
        int expectedLatestYear = Year.now().getValue() - 5;
        List<Integer> result = generalService.getActiveBirthYears();
        assertFalse(result.isEmpty());
        assertEquals(expectedEarliestYear, result.get(0));
        assertEquals(expectedLatestYear, result.get(result.size() - 1));
    }

    @Test
    void testGetActiveStartYears() {
        int expectedEarliestYear = 2018;
        int expectedLatestYear = Year.now().getValue();
        List<Integer> result = generalService.getActiveStartYears();
        assertFalse(result.isEmpty());
        assertEquals(expectedEarliestYear, result.get(0));
        assertEquals(expectedLatestYear, result.get(result.size() - 1));
    }

    @Test
    void testGetBirthYearsFromScoutGroupAndYearAndTerm_VT() {
        ScoutGroup testScoutGroup = new ScoutGroup();
        testScoutGroup.setMinAge(8);
        testScoutGroup.setMaxAge(9);
        int year = 2020;
        Term term = Term.VT;
        List<Integer> expectedResult = List.of(2010, 2011);

        List<Integer> actualResult = generalService.getBirthYearsFromScoutGroupAndYearAndTerm(testScoutGroup, year, term);

        assertEquals(expectedResult.get(0), actualResult.get(0));
        assertEquals(expectedResult.get(1), actualResult.get(1));
    }

    @Test
    void testGetBirthYearsFromScoutGroupAndYearAndTerm_HT() {
        ScoutGroup testScoutGroup = new ScoutGroup();
        testScoutGroup.setMinAge(8);
        testScoutGroup.setMaxAge(9);
        int year = 2020;
        Term term = Term.HT;
        List<Integer> expectedResult = List.of(2011, 2012);

        List<Integer> actualResult = generalService.getBirthYearsFromScoutGroupAndYearAndTerm(testScoutGroup, year, term);

        assertEquals(expectedResult.get(0), actualResult.get(0));
        assertEquals(expectedResult.get(1), actualResult.get(1));
    }
}