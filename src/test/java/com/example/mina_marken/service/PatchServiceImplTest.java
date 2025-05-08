package com.example.mina_marken.service;

import com.example.mina_marken.model.entity.Patch;
import com.example.mina_marken.model.entity.ScoutGroup;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Transactional
class PatchServiceImplTest {

    @Autowired
    private PatchServiceImpl patchService;

    @Test
    void getPatchesFromIdCode() {
        String scoutID = "2024VT2015";
        List<Patch> actualResult = patchService.getPatchesFromIdCode(scoutID);
        assertEquals(2, actualResult.size());
        assertEquals(1L, actualResult.get(0).getId());
        assertEquals(2L, actualResult.get(1).getId());
    }

    @Test
    void getPatchesFromCurrentScoutGroup() {
        ScoutGroup scoutGroup = new ScoutGroup(1L, "testGroup_one", 8, 9);
        List<Patch> actualResult = patchService.getPatchesFromCurrentScoutGroup(scoutGroup);
        assertEquals(2, actualResult.size());
        assertEquals(1L, actualResult.get(0).getId());
        assertEquals("testPatch_two", actualResult.get(1).getName());
    }

    @Test
    void getPatchFromPatchId() {
        Patch actualResult = patchService.getPatchFromPatchId(1L);
        assertEquals("testPatch_one", actualResult.getName());
    }

    @Test
    void getPatchFromName() {
        Patch actualResult = patchService.getPatchFromName("testPatch_one");
        assertEquals(1L, actualResult.getId());
    }

    @Test
    void getAllPatches() {
        List<Patch> actualResult = patchService.getAllPatches();
        assertEquals(3, actualResult.size());
    }
}