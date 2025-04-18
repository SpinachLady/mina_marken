package com.example.mina_marken.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/leader")
public class LeaderViewController {

    @RequestMapping("/createAccount")
    public String getCreateAccountPage(Model model) {
        return "leader/createAccount";
    }

    @RequestMapping("/main")
    public String getMainPage(Model model) {
        return "leader/main";
    }

    @RequestMapping("/specific{id}")
    public String getSpecificPage(Model model) {
        return "leader/specific";
    }

}
