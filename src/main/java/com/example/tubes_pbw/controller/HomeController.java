package com.example.tubes_pbw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.tubes_pbw.model.artis.Artis;
import com.example.tubes_pbw.model.artis.ArtisService;

@Controller
public class HomeController {
    @Autowired
    ArtisService artisService;
    @GetMapping("/")
    public String showHome(Model model){
        Iterable<Artis> listArtis = artisService.findByFilterNamaArtis("");
        model.addAttribute("listArtis", listArtis);
        return "homePage";
    }

}
