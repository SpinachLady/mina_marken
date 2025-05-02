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
import org.springframework.web.bind.annotation.RequestMapping;
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
        model.addAttribute("patchOrders", organizationPatches);
        return "admin/showList";
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

}
