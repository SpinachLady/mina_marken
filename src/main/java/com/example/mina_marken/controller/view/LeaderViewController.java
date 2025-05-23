package com.example.mina_marken.controller.view;

import com.example.mina_marken.model.entity.Patch;
import com.example.mina_marken.model.entity.PatchOrder;
import com.example.mina_marken.model.entity.ScoutGroup;
import com.example.mina_marken.service.PatchOrderService;
import com.example.mina_marken.service.PatchService;
import com.example.mina_marken.service.ScoutGroupService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller
@RequestMapping("/leader")
public class LeaderViewController {

    private final PatchService ps;
    private final PatchOrderService pos;
    private final ScoutGroupService sgs;

    public LeaderViewController(PatchService ps, PatchOrderService pos, ScoutGroupService sgs) {
        this.ps = ps;
        this.pos = pos;
        this.sgs = sgs;
    }

    @RequestMapping("/createAccount")
    public String getCreateAccountPage(Model model) {
        model.addAttribute("groups", sgs.getAllScoutGroups());
        return "leader/createAccount";
    }

    @RequestMapping("/main/{groupName}")
    public String getMainPage(Model model, @PathVariable String groupName) {
        ScoutGroup scoutGroup = sgs.getScoutGroupFromName(groupName);
        List<Patch> patches = ps.getPatchesFromCurrentScoutGroup(scoutGroup);
        model.addAttribute("scoutGroup", scoutGroup);
        model.addAttribute("patches", patches);
        return "leader/main";
    }

    @RequestMapping("/specific/{groupName}/{patchID}")
    public String getSpecificPage(Model model, @PathVariable String groupName, @PathVariable Long patchID) {
        Patch patch = ps.getPatchFromPatchId(patchID);
        ScoutGroup group = sgs.getScoutGroupFromName(groupName);
        List<PatchOrder> patchOrders = pos.getPatchOrderFromGroupIDAndPatch(group, patch);
        String infoText = pos.getInfoTextFromPatchOrders(patchOrders);
        model.addAttribute("infoText", infoText);
        model.addAttribute("patch", patch);
        return "leader/specific";
    }

}
