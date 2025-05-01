package com.example.mina_marken.service;

import com.example.mina_marken.model.Term;
import com.example.mina_marken.model.entity.Patch;
import com.example.mina_marken.model.entity.PatchOrder;
import com.example.mina_marken.model.entity.ScoutGroup;
import com.example.mina_marken.repo.PatchOrderRepo;
import com.example.mina_marken.repo.ScoutGroupRepo;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PatchOrderServiceImpl implements PatchOrderService{

    private final PatchOrderRepo patchOrderRepo;
    private final ScoutGroupRepo scoutGroupRepo;
    private final GeneralService generalService;

    public PatchOrderServiceImpl(PatchOrderRepo patchOrderRepo, ScoutGroupRepo scoutGroupRepo, GeneralService generalService) {
        this.patchOrderRepo = patchOrderRepo;
        this.scoutGroupRepo = scoutGroupRepo;
        this.generalService = generalService;
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
        Term currentTerm;
        LocalDate midYear = LocalDate.of(Year.now().getValue(), 6, 30);
        if (LocalDate.now().isAfter(midYear)) {
            currentTerm = Term.HT;
        }
        else {
            currentTerm = Term.VT;
        }
        List<Integer> birthYears = generalService.getBirthYearsFromScoutGroupAndYearAndTerm(scoutGroup, Year.now().getValue(), currentTerm);
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
            PatchOrder po = patchOrders.get(0);
            infoText.append(" ").append(po.getTerm()).append("-").append(po.getYear());
        } else {
            for (int i = 0; i < patchOrders.size(); i++) {
                PatchOrder po = patchOrders.get(i);
                if (i == patchOrders.size() - 1) {
                    infoText.append("och ").append(po.getTerm()).append("-").append(po.getYear());
                } else {
                    infoText.append(" ").append(po.getTerm()).append("-").append(po.getYear()).append(", ");
                }
            }
        }

        infoText.append(".");
        return infoText.toString();
    }


    public void savePatchOrder(PatchOrder patchOrder) {
        patchOrderRepo.save(patchOrder);
    }

    public List<PatchOrder> getAllPatchOrders() {
        return patchOrderRepo.findAll();
    }

    public PatchOrder getPatchOrderFromID(Long ID) {
        return patchOrderRepo.findById(ID).orElse(null);
    }
    public void deletePatchOrder(PatchOrder patchOrder) {
        patchOrderRepo.delete(patchOrder);
    }

    private boolean birthYearMatchesScoutGroupInPatchOrder(int birthYear, PatchOrder patchOrder) {
        int age;
        if (patchOrder.getTerm().equals(Term.VT)) {
            age = (patchOrder.getYear() - birthYear) - 1;
        } else {
            age = patchOrder.getYear() - birthYear;
        }
        ScoutGroup sg = scoutGroupRepo.findByAgeInRange(age);
        return patchOrder.getScoutGroup().equals(sg);
    }

    private boolean startTermIsEarlierThanPatchOrderTime(int startYear, Term startTerm, PatchOrder patchOrder) {
        if (patchOrder.getYear() < startYear) {
            return false;
        }
        return patchOrder.getYear() != startYear || patchOrder.getTerm() != Term.VT || startTerm != Term.HT;
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
