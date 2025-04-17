package com.example.mina_marken.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class MinaMarkenController {

    @GetMapping("/patch")
    public void getAllPatches() {}

    @GetMapping("/patch/organization")
    public void getOrganizationPatches() {}

    @GetMapping("/patch/{patchID}")
    public void getPatchById() {}
}
