package com.example.mina_marken.controller.view;

import com.example.mina_marken.model.entity.Patch;
import com.example.mina_marken.model.entity.PatchOrder;
import com.example.mina_marken.model.entity.ScoutGroup;
import com.example.mina_marken.service.GeneralService;
import com.example.mina_marken.service.PatchOrderService;
import com.example.mina_marken.service.PatchService;
import com.example.mina_marken.service.ScoutGroupService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminViewController {

    private final PatchService ps;
    private final PatchOrderService pos;
    private final ScoutGroupService sgs;
    private final GeneralService generalService;

    public AdminViewController(PatchService ps, PatchOrderService pos, ScoutGroupService sgs, GeneralService generalService) {
        this.ps = ps;
        this.pos = pos;
        this.sgs = sgs;
        this.generalService = generalService;
    }

    @RequestMapping("/start")
    public String getStartPage() {
        return "admin/start";
    }

    @RequestMapping("/showList")
    public String getShowListPage(Model model) {
        List<PatchOrder> organizationPatches = pos.getAllPatchOrders();
        return getString(model, organizationPatches);
    }
    @GetMapping("/showAdvancedSearchList")
    public String getShowAdvancedSearchListPage(Model model, @RequestParam(required = false) String patchName, @RequestParam(required = false) String scoutGroup, @RequestParam(required = false) String termValue, @RequestParam(required = false) String yearValue) {
        List<PatchOrder> organizationPatches = pos.getPatchOrdersFromAdvancedSearch(patchName, scoutGroup, termValue, yearValue);
        return getString(model, organizationPatches);
    }

    @RequestMapping("/showArchivedList")
    public String getShowArchivedListPage(Model model) {
        List<PatchOrder> organizationPatches = pos.getAllArchivedPatchOrders();
        model.addAttribute("patchOrders", organizationPatches);
        return "admin/showArchivedList";
    }

    @RequestMapping("/addPatch")
    public String getAddPatchPage(Model model) {
        List<ScoutGroup> groups = sgs.getAllScoutGroups();
        List<Patch> patches = ps.getAllPatches();
        List<Integer> years = generalService.getActiveStartYears();
        model.addAttribute("years", years);
        model.addAttribute("groups", groups);
        model.addAttribute("patches", patches);
        return "admin/addPatch";
    }

    private String getString(Model model, List<PatchOrder> organizationPatches) {
        List<Patch> allPatches = ps.getAllPatches();
        List<Integer> years = generalService.getActiveStartYears();
        List<ScoutGroup> groups = sgs.getAllScoutGroups();
        model.addAttribute("patchOrders", organizationPatches);
        model.addAttribute("allPatches", allPatches);
        model.addAttribute("groups", groups);
        model.addAttribute("years", years);
        return "admin/showList";
    }

}
