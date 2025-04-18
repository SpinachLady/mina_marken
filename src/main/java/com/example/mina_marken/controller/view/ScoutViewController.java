package com.example.mina_marken.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/scout")
public class ScoutViewController {

    @RequestMapping("/createAccount")
    public String getCreateAccountPage(Model model) {
        return "scout/createAccount";
    }

    @RequestMapping("/main")
    public String getMainPage(Model model) {
        return "scout/main";
    }

    @RequestMapping("/specific{id}")
    public String getSpecificPage(Model model) {
        return "scout/specific";
    }

}
