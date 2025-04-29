package com.example.mina_marken.controller.view;

import com.example.mina_marken.model.entity.Patch;
import com.example.mina_marken.model.entity.PatchOrder;
import com.example.mina_marken.service.GeneralService;
import com.example.mina_marken.service.PatchOrderService;
import com.example.mina_marken.service.PatchService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/scout")
public class ScoutViewController {

    private final PatchService ps;
    private final PatchOrderService pos;
    private final GeneralService generalService;

    public ScoutViewController(PatchService ps, PatchOrderService pos, GeneralService generalService) {
        this.ps = ps;
        this.pos = pos;
        this.generalService = generalService;
    }

    @RequestMapping("/createAccount")
    public String getCreateAccountPage(Model model) {
        List<Integer> birthYears = generalService.getActiveBirthYears();
        List<Integer> startYears = generalService.getActiveStartYears();
        model.addAttribute("birthYears", birthYears);
        model.addAttribute("startYears", startYears);
        return "scout/createAccount";
    }

    @RequestMapping("/main/{scoutID}")
    public String getMainPage(Model model, @PathVariable String scoutID) {
        List<Patch> patches = ps.getPatchesFromIdCode(scoutID);
        model.addAttribute("patches", patches);
        model.addAttribute("scoutID", scoutID);
        return "scout/main";
    }

    @RequestMapping("/specific/{scoutID}/{patchId}")
    public String getSpecificPage(Model model, @PathVariable String scoutID, @PathVariable Long patchId) {
        Patch patch = ps.getPatchFromPatchId(patchId);
        PatchOrder patchOrder = pos.getPatchOrderFromScoutIDAndPatch(scoutID, patch);
        model.addAttribute("patchOrder", patchOrder);
        model.addAttribute("patch", patch);
        return "scout/specific";
    }

}
