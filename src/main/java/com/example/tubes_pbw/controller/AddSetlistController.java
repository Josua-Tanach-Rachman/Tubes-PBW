package com.example.tubes_pbw.controller;

import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.tubes_pbw.model.artis.Artis;
import com.example.tubes_pbw.model.artis.ArtisService;
import com.example.tubes_pbw.model.kota.Kota;
import com.example.tubes_pbw.model.kota.KotaService;
import com.example.tubes_pbw.model.lokasi.Lokasi;
import com.example.tubes_pbw.model.lokasi.LokasiService;
import com.example.tubes_pbw.model.negara.Negara;
import com.example.tubes_pbw.model.negara.NegaraService;

@Controller
public class AddSetlistController {

    @Autowired
    ArtisService artisService;

    @Autowired
    NegaraService negaraService;

    @Autowired
    KotaService kotaService;

    @Autowired
    LokasiService lokasiService;
    
    @GetMapping("/add/setlist/artist")
    @ResponseBody
    public Iterable<Artis> getAllArtis() {
        return artisService.findByFilterNamaArtis("");
    }

    @GetMapping("/add/setlist/negara")
    @ResponseBody
    public Iterable<Negara> getAllNegara() {
        return negaraService.findByFilterNamaNegara("");
    }

    @GetMapping("/add/setlist/kota")
    @ResponseBody
    public Iterable<Kota> getAllKotaInNegara(@RequestParam("namaNegara") String namaNegara) {
        Iterable<Negara> listNeg = negaraService.findByFilterNamaNegara(namaNegara);
        Iterator<Negara> iterator = listNeg.iterator();
        Negara neg = iterator.hasNext()? iterator.next() : null;
        return kotaService.findByIdNegara(neg.getIdNegara());
    }

    @GetMapping("/add/setlist/lokasi")
    @ResponseBody
    public Iterable<Lokasi> getAllVenueInKota(@RequestParam("namaKota") String namaKota) {
        Iterable<Kota> listKota = kotaService.findByFilterNamaKota(namaKota);
        Iterator<Kota> iterator = listKota.iterator();
        Kota kota = iterator.hasNext()? iterator.next() : null;
        return lokasiService.findByIdKota(kota.getIdKota());
    }
}
