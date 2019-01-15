package com.jtm.FCCweb.FCCweb.controller;

import com.jtm.FCCweb.FCCweb.FlashcardsCreator;
import com.jtm.FCCweb.FCCweb.GlosbeAPItranslationModel;
import com.jtm.FCCweb.FCCweb.model.FCmaker;
import com.jtm.FCCweb.FCCweb.repository.FCrepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class FccController {

    FlashcardsCreator flashcardsCreator = new FlashcardsCreator();
    GlosbeAPItranslationModel glosbeAPItranslationModel = new GlosbeAPItranslationModel();

    @Autowired
    private FCrepository fcrepository;

    @GetMapping("/fcmaker")
    String fcMakerForm(Model model) {
        model.addAttribute("fcmaker", new FCmaker());
        return "fcmaker";
    }

    @PostMapping("/fcmaker")
    public String fcMakerSubmit(@ModelAttribute FCmaker fcMaker) {
        flashcardsCreator.getDataFromJSON(glosbeAPItranslationModel);
        fcrepository.save(fcMaker);
        return "result";
    }

    @ModelAttribute("transAllWays")
    List<String> getTransAllWays() {
        List<String> transAllWays = new ArrayList<>();
        transAllWays.add("pol -> eng");
        transAllWays.add("eng -> pol");

        return transAllWays;
    }
}
