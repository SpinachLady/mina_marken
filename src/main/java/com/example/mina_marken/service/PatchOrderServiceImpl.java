package com.example.mina_marken.service;

import com.example.mina_marken.model.Term;
import com.example.mina_marken.model.entity.Patch;
import com.example.mina_marken.model.entity.PatchOrder;
import com.example.mina_marken.model.entity.ScoutGroup;
import com.example.mina_marken.repo.PatchOrderRepo;
import com.example.mina_marken.repo.ScoutGroupRepo;
import org.springframework.stereotype.Service;

import java.util.List;

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

    private boolean birthYearMatchesScoutGroupInPatchOrder(int birthYear, PatchOrder patchOrder) {
        int age = patchOrder.getYear() - birthYear;
        ScoutGroup sg = scoutGroupRepo.findByAgeInRange(age);
        System.out.println("scoutgroup1: " + sg);
        System.out.println("scoutgroup2: " + patchOrder.getScoutGroup());
        return patchOrder.getScoutGroup().equals(sg);
    }

    private boolean startTermIsEarlierThanPatchOrderTime (int startYear, Term startTerm, PatchOrder patchOrder) {
        if (patchOrder.getYear() < startYear) {
            return false;
        }
        return patchOrder.getYear() != startYear || patchOrder.getTerm() != Term.VT || startTerm != Term.HT;
    }
}
