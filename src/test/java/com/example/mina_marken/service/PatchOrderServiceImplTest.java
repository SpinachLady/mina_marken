package com.example.mina_marken.service;

import com.example.mina_marken.model.Term;
import com.example.mina_marken.model.entity.Patch;
import com.example.mina_marken.model.entity.PatchOrder;
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
class PatchOrderServiceImplTest {

    @Autowired
    private PatchOrderServiceImpl patchOrderService;

    @Test
    void testGetPatchOrderFromScoutIDAndPatch() {
        String scoutID = "2024VT2015";
        Patch patch = new Patch(1L, "testPatch_one", "testLink_one", null);
        PatchOrder actualResult = patchOrderService.getPatchOrderFromScoutIDAndPatch(scoutID, patch);
        assertNotNull(actualResult);
        assertEquals(2024, actualResult.getOrderYear());
        assertEquals(Term.HT, actualResult.getTerm());
    }

    @Test
    void testGetPatchOrderFromGroupIDAndPatch() {
        ScoutGroup group = new ScoutGroup(1L, "testGroup_one", 8, 9);
        Patch patch = new Patch(1L, "testPatch_one", "testLink_one", null);
        List<PatchOrder> actualResult = patchOrderService.getPatchOrderFromGroupIDAndPatch(group, patch);
        assertEquals(actualResult.size(), 1);
        assertEquals(2024, actualResult.get(0).getOrderYear());
        assertEquals(Term.HT, actualResult.get(0).getTerm());
    }

    @Test
    void testGetInfoTextFromPatchOrders() {
        Patch patch = new Patch(1L, "testpatch", "testUrl", null);
        ScoutGroup scoutGroup = new ScoutGroup(1L, "testgroup", 10, 11);
        PatchOrder patchOrder = new PatchOrder(1L, patch, scoutGroup, Term.HT, 2024, false);
        List<PatchOrder> patchOrderList = List.of(patchOrder);
        String actualResult = patchOrderService.getInfoTextFromPatchOrders(patchOrderList);
        String expectedResult = "Dina scouter har tagit detta märke HT-2024.";
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testGetAllPatchOrders() {
        List<PatchOrder> actualResult = patchOrderService.getAllPatchOrders();
        assertEquals(2, actualResult.size());
        assertEquals(2024, actualResult.get(0).getOrderYear());
        assertEquals(Term.HT, actualResult.get(1).getTerm());
    }

    @Test
    void testGetAllArchivedPatchOrders() {
        List<PatchOrder> actualResult = patchOrderService.getAllArchivedPatchOrders();
        assertEquals(1, actualResult.size());
        assertEquals(2020, actualResult.get(0).getOrderYear());
        assertEquals(Term.VT, actualResult.get(0).getTerm());
    }

    @Test
    void testGetPatchOrderFromID() {
        PatchOrder actualResult = patchOrderService.getPatchOrderFromID(1L);
        assertNotNull(actualResult);
        assertEquals(2024, actualResult.getOrderYear());
        assertEquals(Term.HT, actualResult.getTerm());
    }
    @Test
    void testGetPatchOrderFromID_null() {
        PatchOrder actualResult = patchOrderService.getPatchOrderFromID(5L);
        assertNull(actualResult);
    }
    @Test
    void testGetPatchOrdersFromAdvancedSearch() {
        String patchName = null;
        String scoutGroupName = null;
        String termValue = "HT";
        String yearValue = "2024";
        List<PatchOrder> actualResult = patchOrderService.getPatchOrdersFromAdvancedSearch(patchName, scoutGroupName, termValue, yearValue);
        assertEquals(2, actualResult.size());
    }
}