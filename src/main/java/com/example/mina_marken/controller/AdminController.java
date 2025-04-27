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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        Patch patch = patchService.getPatchFromName(patchName);
        Term term = Term.valueOf(termValue);
        ScoutGroup group = scoutGroupService.getScoutGroupFromName(scoutGroup);
        System.out.println("group (input) " + scoutGroup);
        System.out.println("group 123" + group);
        PatchOrder patchOrder = new PatchOrder();
        patchOrder.setPatch(patch);
        patchOrder.setScoutGroup(group);
        patchOrder.setYear(year);
        patchOrder.setTerm(term);
        System.out.println("patch order: " + patchOrder);
        patchOrderService.savePatchOrder(patchOrder);
        return adminViewController.getAddPatchPage(model);
    }


}