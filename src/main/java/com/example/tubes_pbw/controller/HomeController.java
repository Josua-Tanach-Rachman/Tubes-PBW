package com.example.tubes_pbw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.tubes_pbw.model.artisgambar.ArtisGambar;
import com.example.tubes_pbw.model.artisgambar.ArtisGambarService;

@Controller
public class HomeController {
    @Autowired
    ArtisGambarService agService;

    @GetMapping("/")
    public String showHome(Model model){
        Iterable<ArtisGambar> it = agService.findByFilterNamaArtis("");
        agService.encodeImageDataToBase64((List<ArtisGambar>)it);
        model.addAttribute("listArtis", it);
        return "homePage";
    }

}
