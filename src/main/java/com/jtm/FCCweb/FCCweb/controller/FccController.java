package com.jtm.FCCweb.FCCweb.controller;

import com.jtm.FCCweb.FCCweb.model.FCmaker;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FccController {

    @GetMapping("/fcmaker")
    String fcMakerForm(Model model) {
        model.addAttribute("fcmaker", new FCmaker());
        return "fcmaker";
    }

    @PostMapping("/fcmaker")
    public String fcMakerSubmit(@ModelAttribute FCmaker fcMaker) {
        return "result";
    }
}
