package com.jtm.FCCweb.FCCweb.controller;

import com.jtm.FCCweb.FCCweb.FlashcardsCreator;
import com.jtm.FCCweb.FCCweb.model.FCmaker;
import com.jtm.FCCweb.FCCweb.repository.FCrepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
public class FccController {

    FlashcardsCreator flashcardsCreator = new FlashcardsCreator();

    @Autowired
    private FCrepository fcrepository;

    @GetMapping("/fcmaker")
    String fcMakerForm(Model model) {
        model.addAttribute("fcmaker", new FCmaker());
        return "fcmaker";
    }

    @PostMapping("/fcmaker")
    public String fcMakerSubmit(@ModelAttribute FCmaker fcMaker) {
        flashcardsCreator.setDataFromJSON(fcMaker);
        //flashcardsCreator.getExamples(fcMaker);
        flashcardsCreator.setExamplesURL(fcMaker);
        fcrepository.save(fcMaker);
        return "result";
    }

    @GetMapping("/result")
    public String getFlashcards(Model model) {
        model.addAttribute("flashcards", fcrepository.findAll()); //Ten atrybut jest potem używany przez thymeleaf do wyswietlania wynikow
        // w pierwszym argumentem w addAtribute() jest nazwa nowej zmiennej, a w drugim jej inicjalizacja
        return "result";
    }

    @ModelAttribute("fromLanguages")
    public Map<String,String> getFromLanguages()    {

        Map<String,String> fromLanguages = new HashMap<String, String>();
        fromLanguages.put("eng","english");
        fromLanguages.put("pol","polish");
        return fromLanguages;
    }

    @ModelAttribute("destLanguages")
    public Map<String,String> getDestLanguages()    {

        Map<String,String> destLanguages = new HashMap<String, String>();
        destLanguages.put("eng","english");
        destLanguages.put("pol","polish");
        return destLanguages;
    }
}
