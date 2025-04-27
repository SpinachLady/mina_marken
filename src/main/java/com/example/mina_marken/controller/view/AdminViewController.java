package com.example.mina_marken.controller.view;

import com.example.mina_marken.model.entity.Patch;
import com.example.mina_marken.model.entity.ScoutGroup;
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

    public AdminViewController(PatchService ps, PatchOrderService pos, ScoutGroupService sgs) {
        this.ps = ps;
        this.pos = pos;
        this.sgs = sgs;
    }

    @RequestMapping("/start")
    public String getStartPage() {
        return "admin/start";
    }

    @RequestMapping("/showList")
    public String getShowListPage(Model model) {
        return "admin/showList";
    }

    @RequestMapping("/addPatch")
    public String getAddPatchPage(Model model) {
        List<ScoutGroup> groups = sgs.getAllScoutGroups();
        List<Patch> patches = ps.getAllPatches();
        model.addAttribute("groups", groups);
        model.addAttribute("patches", patches);
        return "admin/addPatch";
    }

}
