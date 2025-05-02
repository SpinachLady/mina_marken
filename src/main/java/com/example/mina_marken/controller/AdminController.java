package com.example.mina_marken.controller;


import com.example.mina_marken.controller.view.AdminViewController;
import com.example.mina_marken.model.Term;
import com.example.mina_marken.model.entity.Patch;
import com.example.mina_marken.model.entity.PatchOrder;
import com.example.mina_marken.model.entity.ScoutGroup;
import com.example.mina_marken.service.PatchOrderService;
import com.example.mina_marken.service.PatchService;
import com.example.mina_marken.service.ScoutGroupService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Controller
@RequestMapping
public class AdminController {

    private final PatchOrderService patchOrderService;
    private final PatchService patchService;

    private final ScoutGroupService scoutGroupService;

    private final AdminViewController adminViewController;

    public AdminController(PatchOrderService patchOrderService, PatchService patchService, ScoutGroupService scoutGroupService, AdminViewController adminViewController) {
        this.patchOrderService = patchOrderService;
        this.patchService = patchService;
        this.scoutGroupService = scoutGroupService;
        this.adminViewController = adminViewController;
    }

    @PostMapping("/addPatchOrder")
    public String addPatchOrder(Model model, @RequestParam String scoutGroup, @RequestParam String termValue, @RequestParam int year, @RequestParam String patchName) {
        try {
            Patch patch = Objects.requireNonNull(patchService.getPatchFromName(patchName));
            Term term = Objects.requireNonNull(Term.valueOf(termValue));
            ScoutGroup group = Objects.requireNonNull(scoutGroupService.getScoutGroupFromName(scoutGroup));
            PatchOrder patchOrder = new PatchOrder();
            patchOrder.setPatch(patch);
            patchOrder.setScoutGroup(group);
            patchOrder.setYear(year);
            patchOrder.setTerm(term);
            patchOrderService.savePatchOrder(patchOrder);
        } catch (Exception e) {
            model.addAttribute("resultColor", "red");
            model.addAttribute("resultMessage", "Ajdå! Något gick fel. Prova igen.");
            return adminViewController.getAddPatchPage(model);
        }
        model.addAttribute("resultColor", "green");
        model.addAttribute("resultMessage", "Bra jobbat! Märket är tillagt.");
        return adminViewController.getAddPatchPage(model);
    }

    @RequestMapping("/archivePatchOrder/{id}")
    public String archivePatchOrder(Model model, @PathVariable Long id) {
        PatchOrder patchOrder = patchOrderService.getPatchOrderFromID(id);
        patchOrder.setArchived(true);
        patchOrderService.savePatchOrder(patchOrder);
        return adminViewController.getShowListPage(model);
    }

    @RequestMapping("/unarchivePatchOrder/{id}")
    public String unarchivePatchOrder(Model model, @PathVariable Long id) {
        PatchOrder patchOrder = patchOrderService.getPatchOrderFromID(id);
        patchOrder.setArchived(false);
        patchOrderService.savePatchOrder(patchOrder);
        return adminViewController.getShowArchivedListPage(model);
    }


}