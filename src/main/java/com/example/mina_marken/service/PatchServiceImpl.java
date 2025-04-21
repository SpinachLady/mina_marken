package com.example.mina_marken.service;

import com.example.mina_marken.model.Term;
import com.example.mina_marken.model.entity.Patch;
import com.example.mina_marken.model.entity.PatchOrder;
import com.example.mina_marken.repo.PatchOrderRepo;
import com.example.mina_marken.repo.PatchRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PatchServiceImpl implements PatchService {

    private final PatchOrderRepo patchOrderRepo;
    private final PatchRepo patchRepo;

    public PatchServiceImpl(PatchOrderRepo patchOrderRepo, PatchRepo patchRepo) {
        this.patchOrderRepo = patchOrderRepo;
        this.patchRepo = patchRepo;
    }

    public List<Patch> getPatchesFromIdCode(String idCode) {
        int startYear = Integer.parseInt(idCode.substring(0, 4));
        Term startTerm = Term.valueOf(idCode.substring(4, 6));
        int birthYear = Integer.parseInt(idCode.substring(6));
        List<PatchOrder> patchOrders = getPatchOrdersFromBirthYearAndStartYearAndStartTerm(birthYear, startYear, startTerm);
        List<Patch> patches = new ArrayList<>();
        for (PatchOrder patchOrder: patchOrders) {
            patches.add(patchRepo.getPatchById(patchOrder.getPatch().getId()));
        }
        return filterUniquePatches(patches);
    }

    private List<PatchOrder> getPatchOrdersFromBirthYearAndStartYearAndStartTerm(int birthYear, int startYear, Term startTerm) {
        List<PatchOrder> patchOrders = new ArrayList<>();
        int age = startYear - birthYear;
        int currentAge = 2025 - birthYear;
        int year = startYear;
        while (age < currentAge) {
            List<PatchOrder> yearPatches = getPatchOrdersFromAgeAndYear(age, year);
            if (yearPatches != null) {
                patchOrders.addAll(yearPatches);
            }
            year++;
            age++;
        }
        return patchOrders;
    }

    //TODO: hämta scoutGroupId från scoutGroup-tabellen istället & gör detta på ett snyggare sätt
    private List<PatchOrder> getPatchOrdersFromAgeAndYear(int age, int year) {
        long scoutGroupId = 0L;
        if (age == 5 || age == 6 || age == 7) {
            scoutGroupId = 1L;
        }
        if (age == 8 || age == 9) {
            scoutGroupId = 2L;
        }
        if (age == 10 || age == 11) {
            scoutGroupId = 3L;
        }
        if (age == 12 || age == 13 || age == 14)  {
            scoutGroupId = 4L;
        }
        if (age == 15 || age == 16 || age == 17) {
            scoutGroupId = 5L;
        }
        if (age > 17) {
            scoutGroupId = 6L;
        }
        return patchOrderRepo.findByYearAndScoutGroup_Id(year, scoutGroupId);
    }

    private List<Patch> filterUniquePatches(List<Patch> patches) {
        return patches.stream()
                .collect(Collectors.toMap(
                        Patch::getId,
                        Function.identity(),
                        (existing, replacement) -> existing
                ))
                .values()
                .stream()
                .toList();
    }


}
