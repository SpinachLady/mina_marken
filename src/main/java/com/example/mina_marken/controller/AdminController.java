package com.example.mina_marken.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @PostMapping("/addPatch")
    public void addPatchToTerm() {}

    @DeleteMapping("/deletePatch")
    public void deletePatchFromTerm() {}

    @PostMapping("/editPatch")
    public void editPatch() {}

}
