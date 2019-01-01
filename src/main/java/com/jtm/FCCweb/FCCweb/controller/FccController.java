package com.jtm.FCCweb.FCCweb.controller;

import com.jtm.FCCweb.FCCweb.model.FCmaker;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FccController {

    @GetMapping("/fcmaker")
    String addNewFlashcard(Model model) {
        model.addAttribute("fcmaker", new FCmaker());
        return "fcmaker";
    }
}
