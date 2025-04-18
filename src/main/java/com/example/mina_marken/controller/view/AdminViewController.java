package com.example.mina_marken.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminViewController {

    @RequestMapping("/start")
    public String getStartPage(Model model) {
        return "admin/start";
    }

    @RequestMapping("/showList")
    public String getShowListPage(Model model) {
        return "admin/showList";
    }

    @RequestMapping("/addPatch")
    public String getAddPatchPage(Model model) {
        return "admin/addPatch";
    }

}
