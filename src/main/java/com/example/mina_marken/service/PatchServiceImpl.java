package com.example.mina_marken.service;

import com.example.mina_marken.model.Term;
import com.example.mina_marken.model.entity.Patch;
import com.example.mina_marken.model.entity.PatchOrder;
import com.example.mina_marken.model.entity.ScoutGroup;
import com.example.mina_marken.repo.PatchOrderRepo;
import com.example.mina_marken.repo.PatchRepo;
import com.example.mina_marken.repo.ScoutGroupRepo;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PatchServiceImpl implements PatchService {

    private final PatchOrderRepo patchOrderRepo;
    private final PatchRepo patchRepo;
    private final ScoutGroupRepo scoutGroupRepo;
    private final GeneralService generalService;

    public PatchServiceImpl(PatchOrderRepo patchOrderRepo, PatchRepo patchRepo, ScoutGroupRepo scoutGroupRepo, GeneralService generalService) {
        this.patchOrderRepo = patchOrderRepo;
        this.patchRepo = patchRepo;
        this.scoutGroupRepo = scoutGroupRepo;
        this.generalService = generalService;
    }

    public List<Patch> getPatchesFromIdCode(String idCode) {
        int startYear = Integer.parseInt(idCode.substring(0, 4));
        Term startTerm = Term.valueOf(idCode.substring(4, 6));
        int birthYear = Integer.parseInt(idCode.substring(6));
        List<PatchOrder> patchOrders = getPatchOrdersFromBirthYearAndStartYearAndStartTerm(birthYear, startYear, startTerm);
        List<Patch> patches = getPatchesFromPatchOrders(patchOrders);
        return filterUniquePatches(patches);
    }
    public List<Patch> getPatchesFromCurrentScoutGroup(ScoutGroup scoutGroup) {
        int minBirthYear = getBirthYearFromAge(scoutGroup.getMinAge());
        int maxBirthYear = getBirthYearFromAge(scoutGroup.getMaxAge());
        if (generalService.getCurrentTerm().equals(Term.VT)) {
            minBirthYear--;
            maxBirthYear--;
        }
        List<Integer> birthYears = getBirthYearsBetween(minBirthYear, maxBirthYear);
        List<PatchOrder> patchOrders = new ArrayList<>();
        for (Integer birthYear : birthYears) {
            patchOrders.addAll(getPatchOrdersFromBirthYearAndStartYearAndStartTerm(birthYear, birthYear + 5, Term.HT));
        }
        List<Patch> patches = getPatchesFromPatchOrders(patchOrders);
        return filterUniquePatches(patches);
    }
    public Patch getPatchFromPatchId(Long id) {
        return patchRepo.getPatchById(id);
    }
    public Patch getPatchFromName(String name) {
        return patchRepo.getPatchByName(name);
    }

    public List<Patch> getAllPatches() {
        return patchRepo.findAll();
    }

    private List<PatchOrder> getPatchOrdersFromBirthYearAndStartYearAndStartTerm(int birthYear, int startYear, Term startTerm) {
        List<PatchOrder> patchOrders = new ArrayList<>();
        int startAge = startYear - birthYear;
        int currentAge = Year.now().getValue() - birthYear;
        int year = startYear;
        List<PatchOrder> startYearPatches = getPatchOrdersFromAgeAndYearAndTerm(startAge, year, startTerm);
        if (startTerm.equals(Term.VT)) {
            startYearPatches.addAll(getPatchOrdersFromAgeAndYearAndTerm(startAge, year, Term.HT));
        }
        startAge++;
        year++;
        while (startAge <= currentAge) {
            List<PatchOrder> yearPatches1 = getPatchOrdersFromAgeAndYearAndTerm(startAge, year, Term.VT);
            List<PatchOrder> yearPatches2 = getPatchOrdersFromAgeAndYearAndTerm(startAge, year, Term.HT);
            patchOrders.addAll(yearPatches1);
            patchOrders.addAll(yearPatches2);
            year++;
            startAge++;
        }
        patchOrders.addAll(startYearPatches);
        return patchOrders;
    }

    private List<PatchOrder> getPatchOrdersFromAgeAndYearAndTerm(int age, int year, Term term) {
        ScoutGroup group;
        if (term.equals(Term.VT)) {
            group = scoutGroupRepo.findByAgeInRange(age - 1);
        } else {
            group = scoutGroupRepo.findByAgeInRange(age);
        }
        return patchOrderRepo.findActiveByYearAndTermAndScoutGroup(year, term, group);
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

    private List<Patch> getPatchesFromPatchOrders(List<PatchOrder> patchOrders) {
        List<Patch> patches = new ArrayList<>();
        for (PatchOrder patchOrder: patchOrders) {
            patches.add(patchRepo.getPatchById(patchOrder.getPatch().getId()));
        }
        return patches;
    }

    private int getBirthYearFromAge(int age) {
        return Year.now().getValue() - age;
    }

    private List<Integer> getBirthYearsBetween(int minBirthYear, int maxBirthYear) {
        List<Integer> birthYears = new ArrayList<>();
        while (maxBirthYear <= minBirthYear) {
            birthYears.add(maxBirthYear);
            maxBirthYear++;
        }
        return birthYears;
    }
}
