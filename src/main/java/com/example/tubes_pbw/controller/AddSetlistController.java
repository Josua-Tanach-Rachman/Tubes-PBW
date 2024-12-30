package com.example.tubes_pbw.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.tubes_pbw.model.artis.Artis;
import com.example.tubes_pbw.model.artis.ArtisService;
import com.example.tubes_pbw.model.kota.Kota;
import com.example.tubes_pbw.model.kota.KotaService;
import com.example.tubes_pbw.model.lokasi.Lokasi;
import com.example.tubes_pbw.model.lokasi.LokasiService;
import com.example.tubes_pbw.model.negara.Negara;
import com.example.tubes_pbw.model.negara.NegaraService;
import com.example.tubes_pbw.model.setlist.SetlistService;
import com.example.tubes_pbw.model.user.User;

@Controller
public class AddSetlistController {

    @Value("${file.upload-dir}")
    String uploadDir;

    @Autowired
    SetlistService setlistService;

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

    @PostMapping("/addsetlist")
    public String addsetlist(
        @RequestParam("artist-name") String namaArtis,
        @RequestParam("country") String namaNegara,
        @RequestParam("city") String namaKota,
        @RequestParam("venue") String namaLokasi,
        @RequestParam("date") String date,
        @RequestParam("file") MultipartFile file,
        Model model,User user) throws IOException
    {
        String namaSetlist = namaArtis + " at " + namaLokasi;
        String namaImage = (namaArtis + namaLokasi).replaceAll("\\s+", "");
        String path = saveImage("bukti", file,namaImage);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Adjusted for 
        LocalDate localDate = LocalDate.parse(date, formatter);
        
        Timestamp timestamp = Timestamp.valueOf(localDate.atStartOfDay());

        Artis artis = artisService.findByNamaArtis(namaArtis).get();

        Lokasi lokasi = lokasiService.findByNamaLokasi(namaLokasi).get();

        int idSetlist = setlistService.save(namaSetlist, timestamp, artis.getIdArtis(), lokasi.getIdLokasi(), path);

        return "redirect:/setlist";
    }

    public String saveImage(String subDir, MultipartFile file, String namaImage) throws IOException {
        Path uploadPath = Paths.get(uploadDir, subDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String fileExtension = getFileExtension(file.getOriginalFilename());

        // String fileName = file.getOriginalFilename();
        Path filePath = uploadPath.resolve(namaImage + fileExtension);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // return filePath.toString();
        return "/" + subDir + "/" + namaImage + fileExtension;
    }

    public String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0) {
            return fileName.substring(dotIndex);
        } else {
            return ""; 
        }
    }
}
