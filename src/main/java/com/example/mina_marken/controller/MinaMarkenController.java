package com.example.mina_marken.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping
public class MinaMarkenController {

    @RequestMapping("/index")
    public String getStartPage() {
        return "index";
    }

    @GetMapping("/patch")
    public void getAllPatches() {}

    @GetMapping("/patch/organization")
    public void getOrganizationPatches() {}

    @GetMapping("/patch/{patchID}")
    public void getPatchById() {}
}
