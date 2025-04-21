package com.example.mina_marken.controller.view;

import com.example.mina_marken.model.entity.Patch;
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

    public ScoutViewController(PatchService ps) {
        this.ps = ps;
    }

    @RequestMapping("/createAccount")
    public String getCreateAccountPage(Model model) {
        return "scout/createAccount";
    }

    @RequestMapping("/main/{scoutID}")
    public String getMainPage(Model model, @PathVariable String scoutID) {
        List<Patch> patches = ps.getPatchesFromIdCode(scoutID);
        model.addAttribute("patches", patches);
        return "scout/main";
    }

    @RequestMapping("/specific{id}")
    public String getSpecificPage(Model model) {
        return "scout/specific";
    }

}
