package com.example.mina_marken.service;

import com.example.mina_marken.model.Term;
import com.example.mina_marken.model.entity.Patch;
import com.example.mina_marken.model.entity.PatchOrder;
import com.example.mina_marken.model.entity.ScoutGroup;
import com.example.mina_marken.repo.PatchOrderRepo;
import com.example.mina_marken.repo.ScoutGroupRepo;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PatchOrderServiceImpl implements PatchOrderService{

    private final PatchOrderRepo patchOrderRepo;
    private final ScoutGroupRepo scoutGroupRepo;

    public PatchOrderServiceImpl(PatchOrderRepo patchOrderRepo, ScoutGroupRepo scoutGroupRepo) {
        this.patchOrderRepo = patchOrderRepo;
        this.scoutGroupRepo = scoutGroupRepo;
    }

    public PatchOrder getPatchOrderFromScoutIDAndPatch(String scoutID, Patch patch) {
        int birthYear = Integer.parseInt(scoutID.substring(6));
        int startYear = Integer.parseInt(scoutID.substring(0, 4));
        Term startTerm = Term.valueOf(scoutID.substring(4, 6));
        List<PatchOrder> patchOrderList = patchOrderRepo.findByPatch(patch);
        if (patchOrderList.size() > 1) {
            for (PatchOrder patchOrder : patchOrderList) {
                if (birthYearMatchesScoutGroupInPatchOrder(birthYear, patchOrder) && startTermIsEarlierThanPatchOrderTime(startYear, startTerm, patchOrder)) {
                    return patchOrder;
                }
            }
        }
        return patchOrderList.get(0);
    }

    public List<PatchOrder> getPatchOrderFromGroupIDAndPatch(ScoutGroup scoutGroup, Patch patch) {
        List<Integer> birthYears = getBirthYearsFromScoutGroupAndYear(scoutGroup, Year.now().getValue());
        List<PatchOrder> patchOrderList = patchOrderRepo.findByPatch(patch);
        List<PatchOrder> correctPatchOrders = new ArrayList<>();
        for (Integer birthYear : birthYears) {
            for (PatchOrder patchOrder : patchOrderList) {
                if (birthYearMatchesScoutGroupInPatchOrder(birthYear, patchOrder)) {
                    correctPatchOrders.add(patchOrder);
                }
            }
        }
        return filterUniquePatchOrders(correctPatchOrders);
    }

    public String getInfoTextFromPatchOrders(List<PatchOrder> patchOrders) {
        StringBuilder infoText = new StringBuilder("Dina scouter har tagit detta märke");
        if (patchOrders.size() == 1) {
            infoText.append(" ").append(patchOrders.get(0).getTerm()).append("-").append(patchOrders.get(0).getYear());
        }
        else {
            infoText.append(" ");
            while (patchOrders.size() > 1) {
                infoText.append(patchOrders.get(0).getTerm()).append("-").append(patchOrders.get(0).getYear()).append(", ");
                patchOrders.remove(0);
            }
            infoText.append("och ").append(patchOrders.get(0).getYear());
        }
        infoText.append(".");
        return String.valueOf(infoText);
    }

    public void savePatchOrder(PatchOrder patchOrder) {
        patchOrderRepo.save(patchOrder);
    }

    public List<PatchOrder> getAllPatchOrders() {
        return patchOrderRepo.findAll();
    }

    private boolean birthYearMatchesScoutGroupInPatchOrder(int birthYear, PatchOrder patchOrder) {
        int age = patchOrder.getYear() - birthYear;
        ScoutGroup sg = scoutGroupRepo.findByAgeInRange(age);
        return patchOrder.getScoutGroup().equals(sg);
    }

    private boolean startTermIsEarlierThanPatchOrderTime(int startYear, Term startTerm, PatchOrder patchOrder) {
        if (patchOrder.getYear() < startYear) {
            return false;
        }
        return patchOrder.getYear() != startYear || patchOrder.getTerm() != Term.VT || startTerm != Term.HT;
    }

    private List<Integer> getBirthYearsFromScoutGroupAndYear(ScoutGroup scoutGroup, int year) {
        int minBirthYear = year - scoutGroup.getMinAge();
        int maxBirthYear = year - scoutGroup.getMaxAge();
        return getBirthYearsBetween(minBirthYear, maxBirthYear);
    }

    private List<Integer> getBirthYearsBetween(int minBirthYear, int maxBirthYear) {
        List<Integer> birthYears = new ArrayList<>();
        while (maxBirthYear <= minBirthYear) {
            birthYears.add(maxBirthYear);
            maxBirthYear++;
        }
        return birthYears;
    }

    private List<PatchOrder> filterUniquePatchOrders(List<PatchOrder> patchOrders) {
        return patchOrders.stream()
                .collect(Collectors.toMap(
                        PatchOrder::getId,
                        Function.identity(),
                        (existing, replacement) -> existing
                ))
                .values()
                .stream()
                .toList();
    }
}
