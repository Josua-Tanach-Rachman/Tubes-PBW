package com.example.tubes_pbw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.tubes_pbw.model.album.Album;
import com.example.tubes_pbw.model.album.AlbumService;
import com.example.tubes_pbw.model.artis.Artis;
import com.example.tubes_pbw.model.artis.ArtisService;
import com.example.tubes_pbw.model.lagu.Lagu;
import com.example.tubes_pbw.model.lagu.LaguService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
    @Autowired
    ArtisService artisService;
    @Autowired
    AlbumService albumService;
    @Autowired
    LaguService laguService;
    @GetMapping("/")
    public String showHome(Model model, HttpSession session){
        Iterable<Artis> listArtis = artisService.findByFilterNamaArtis("");
        model.addAttribute("listArtis", listArtis);

        Iterable<Album> listAlbum = albumService.findAll();
        model.addAttribute("listAlbum", listAlbum);

        List<Artis> listArtisTop = artisService.findTopArtisBySetlistLagu();
        model.addAttribute("listArtisTop", listArtisTop);

        List<Lagu> listSongTop = laguService.findTopSong_slideShow();
        model.addAttribute("listSongTop", listSongTop);

        if(session.getAttribute("username") == null){
            model.addAttribute("isUserLoggedIn", false);
            return "homePage";
        }
        else{
            model.addAttribute("isUserLoggedIn", true);
            if(session.getAttribute("role").equals("admin")){
                model.addAttribute("isAdmin", true);
                return "homePage_admin";
            } else {
                if ((Boolean)session.getAttribute("status") == true){
                    model.addAttribute("isAdmin", false);
                    return "homePage";
                }
                else {
                    model.addAttribute("isUserLoggedIn", false);
                    return "homePage";
                }
            }
        }
    }
}
