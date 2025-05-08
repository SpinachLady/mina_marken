package com.example.mina_marken.service;

import com.example.mina_marken.model.entity.ScoutGroup;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Transactional
class ScoutGroupServiceImplTest {

    @Autowired
    private ScoutGroupServiceImpl scoutGroupService;

    @Test
    void getAllScoutGroups() {
        ScoutGroup sg1 = new ScoutGroup(1L, "testGroup_one", 8, 9);
        ScoutGroup sg2 = new ScoutGroup(2L, "testGroup_two", 12, 14);
        ScoutGroup sg3 = new ScoutGroup(3L, "testGroup_three", 15, 17);
        List<ScoutGroup> expectedResult = List.of(sg1, sg2, sg3);
        List<ScoutGroup> actualResult = scoutGroupService.getAllScoutGroups();
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void getScoutGroupFromName() {
        ScoutGroup expectedResult = new ScoutGroup(1L, "testGroup_one", 8, 9);
        ScoutGroup actualResult = scoutGroupService.getScoutGroupFromName("testGroup_one");
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void getScoutGroupFromID() {
        ScoutGroup expectedResult = new ScoutGroup(1L, "testGroup_one", 8, 9);
        ScoutGroup actualResult = scoutGroupService.getScoutGroupFromID(1L);
        assertEquals(expectedResult, actualResult);
    }
}