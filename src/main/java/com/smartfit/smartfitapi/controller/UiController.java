package com.smartfit.smartfitapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path = "/api/v1/ui")
public class UiController {
    @GetMapping(path = "/practice")
    public String practice(@RequestParam String description, Model model) {
        model.addAttribute("description", description);
        return "practice";
    }
}
