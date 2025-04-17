package com.example.mina_marken.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/scout")
public class ScoutController {

    @GetMapping("/patches/personal")
    public void getPersonalPatches() {}

}
