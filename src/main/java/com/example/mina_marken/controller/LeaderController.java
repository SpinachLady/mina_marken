package com.example.mina_marken.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/leader")
public class LeaderController {

    @GetMapping("/group")
    public void getAllGroups() {}

    @GetMapping("/group/{groupID}/patches")
    public void getPatchesByGroup() {}
}
