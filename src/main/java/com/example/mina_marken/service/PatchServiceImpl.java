package com.example.mina_marken.service;

import com.example.mina_marken.model.Term;
import com.example.mina_marken.model.entity.Patch;
import com.example.mina_marken.model.entity.PatchOrder;
import com.example.mina_marken.model.entity.ScoutGroup;
import com.example.mina_marken.repo.PatchOrderRepo;
import com.example.mina_marken.repo.PatchRepo;
import com.example.mina_marken.repo.ScoutGroupRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PatchServiceImpl implements PatchService {

    private final PatchOrderRepo patchOrderRepo;
    private final PatchRepo patchRepo;
    private final ScoutGroupRepo scoutGroupRepo;

    public PatchServiceImpl(PatchOrderRepo patchOrderRepo, PatchRepo patchRepo, ScoutGroupRepo scoutGroupRepo) {
        this.patchOrderRepo = patchOrderRepo;
        this.patchRepo = patchRepo;
        this.scoutGroupRepo = scoutGroupRepo;
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

    public Patch getPatchFromPatchId (Long id) {
        return patchRepo.getPatchById(id);
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

    private List<PatchOrder> getPatchOrdersFromAgeAndYear(int age, int year) {
        ScoutGroup group = scoutGroupRepo.findByAgeInRange(age);
        return patchOrderRepo.findByYearAndScoutGroup(year, group);
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
