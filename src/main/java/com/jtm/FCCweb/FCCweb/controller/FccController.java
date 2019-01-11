package com.jtm.FCCweb.FCCweb.controller;

import com.jtm.FCCweb.FCCweb.model.FCmaker;
import com.jtm.FCCweb.FCCweb.repository.FCrepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class FccController {

    @Autowired
    private FCrepository fcrepository;

    @GetMapping("/fcmaker")
    String fcMakerForm(Model model) {
        model.addAttribute("fcmaker", new FCmaker());
        return "fcmaker";
    }

    @PostMapping("/fcmaker")
    public String fcMakerSubmit(@ModelAttribute FCmaker fcMaker) {
        fcrepository.save(fcMaker);
        return "result";
    }
}
