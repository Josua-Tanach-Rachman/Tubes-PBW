package com.example.tubes_pbw.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.tubes_pbw.model.artis.Artis;
import com.example.tubes_pbw.model.artis.ArtisService;
import com.example.tubes_pbw.model.kota.Kota;
import com.example.tubes_pbw.model.kota.KotaService;
import com.example.tubes_pbw.model.lagu.Lagu;
import com.example.tubes_pbw.model.lagu.LaguService;
import com.example.tubes_pbw.model.lokasi.Lokasi;
import com.example.tubes_pbw.model.lokasi.LokasiService;
import com.example.tubes_pbw.model.negara.Negara;
import com.example.tubes_pbw.model.negara.NegaraService;
import com.example.tubes_pbw.model.setlist.Setlist;
import com.example.tubes_pbw.model.setlist.SetlistService;
import com.example.tubes_pbw.model.setlist.SetlistSong;
import com.example.tubes_pbw.model.show.Show;
import com.example.tubes_pbw.model.show.ShowService;
import com.example.tubes_pbw.model.user.PenggunaSetlist;
import com.example.tubes_pbw.model.user.User;
import com.example.tubes_pbw.model.user.UserService;

import jakarta.servlet.http.HttpSession;

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

    @Autowired
    ShowService showService;

    @Autowired
    UserService userService;

    @Autowired
    LaguService laguService;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
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

    @GetMapping("/add/setlist/show")
    @ResponseBody
    public Iterable<Show> getAllConcertInCity(@RequestParam("namaLokasi") String namaLokasi) {
        Iterable<Lokasi> listLokasi = lokasiService.findByFilterNamaLokasi(namaLokasi);
        Iterator<Lokasi> iterator = listLokasi.iterator();
        Lokasi lokasi = iterator.hasNext()? iterator.next() : null;
        return showService.findByIdLokasi(lokasi.getIdLokasi());
    }

    @GetMapping("/add/setlist/lagu")
    @ResponseBody
    public Iterable<Lagu> getAllLagu() {
        Iterable<Lagu> listLagu = laguService.findByNamaLagu("");
        return listLagu;
    }

    @GetMapping("/addsetlist")
    public String addsetlist(User user, Model model, HttpSession session){
        if(session.getAttribute("username") == null){
            model.addAttribute("isUserLoggedIn", false);
        }
        else{
            model.addAttribute("isUserLoggedIn", true);
        }
        if (session.getAttribute("username") == null) {
            return "redirect:/login";
        } else {
            return "addSetlist";
        }
    }

    @PostMapping("/addsetlist")
    public String addsetlist(
        @RequestParam("artist-name") String namaArtis,
        @RequestParam("country") String namaNegara,
        @RequestParam("city") String namaKota,
        @RequestParam("venue") String namaLokasi,
        @RequestParam("show") String namaShow,
        @RequestParam("date") String date,
        @RequestParam("file") MultipartFile file,
        Model model,User user) throws IOException
    {
        String namaSetlist = namaArtis + " at " + namaLokasi;
        String namaImage = (namaArtis + namaLokasi).replaceAll("\\s+", "");
        String path = saveImage("bukti", file,namaImage);

        LocalDate localDate = LocalDate.parse(date, DATE_FORMATTER);
        
        Timestamp timestamp = Timestamp.valueOf(localDate.atStartOfDay());

        Artis artis = artisService.findByNamaArtis(namaArtis).get();

        Lokasi lokasi = lokasiService.findByNamaLokasi(namaLokasi).get();

        Iterable<Show> listShow = showService.findByNamaShow(namaShow);
        Iterator<Show> iterator = listShow.iterator();
        Show show = iterator.hasNext()? iterator.next() : null;

        int idSetlist = setlistService.save(namaSetlist, timestamp, artis.getIdArtis(), lokasi.getIdLokasi(), path,show.getIdShow());

        return "redirect:/setlist";
    }

    @PostMapping("/addArtist")
    public String addArtistToDb(
        @RequestParam("artist-name") String namaArtis,
        @RequestParam("file") MultipartFile file) throws IOException
    {
        String namaImage = namaArtis.replaceAll("\\s+", "");
        String path = saveImage("artis", file,namaImage);
        artisService.save(namaArtis, path);
        return "redirect:/addsetlist";
    }

    @PostMapping("/addConcert")
    public String addConcertToDb(
        @RequestParam("concert-name") String namaConcert,
        @RequestParam("country") String namaNegara,
        @RequestParam("city") String namaKota,
        @RequestParam("venue") String namaLokasi,
        @RequestParam("date") String date,
        @RequestParam("enddate") String endDate,
        User user
    )
    {
        LocalDate beginLocalDate = LocalDate.parse(date, DATE_FORMATTER);
        LocalDate endLocalDate = LocalDate.parse(endDate, DATE_FORMATTER);

        Date sqlBeginDate = Date.valueOf(beginLocalDate);
        Date sqlEndDate = Date.valueOf(endLocalDate);

        Lokasi lokasi = lokasiService.findByNamaLokasi(namaLokasi).get();

        int idShow = showService.saveShow(namaConcert, lokasi.getIdLokasi(), sqlBeginDate, sqlEndDate);
        
        return "redirect:/addsetlist";
    }

    @GetMapping("/edit/setlist/{idSetlist}")
    public String editSetlist(
        @PathVariable int idSetlist, 
        Model model, HttpSession session
    )
    {
        Setlist setlist = setlistService.findByIdSetlist(idSetlist).get();
        Show show = showService.findByIdShow(setlist.getIdShow()).get();
        Lokasi lokasi = lokasiService.findByIdLokasi(setlist.getIdLokasi()).get(0);
        Kota kota = kotaService.findByIdKota(lokasi.getIdKota()).get(0);
        Negara negara = negaraService.findByIdNegara(kota.getIdKota()).get(0);
        
        LocalDate localDate = setlist.getTanggal().toLocalDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = localDate.format(formatter);

        model.addAttribute("setlist", setlist);
        model.addAttribute("show", show);
        model.addAttribute("lokasi", lokasi);
        model.addAttribute("kota", kota);
        model.addAttribute("negara", negara);
        model.addAttribute("date", formattedDate);
        if(session.getAttribute("username") == null){
            model.addAttribute("isUserLoggedIn", false);
        }
        else{
            model.addAttribute("isUserLoggedIn", true);
        }
        if (session.getAttribute("username") == null) {
            return "redirect:/login";
        } else {
            return "halamaneditSetlist";
        }
    }

    @PostMapping("/edit/setlist/{idSetlist}")
    public String editSetlistPost(
        @PathVariable int idSetlist, 
        @RequestParam("country") String namaNegara,
        @RequestParam("city") String namaKota,
        @RequestParam("venue") String namaLokasi,
        @RequestParam("show") String namaShow,
        @RequestParam("date") String date,
        @RequestParam("file") MultipartFile file,
        Model model, HttpSession session
    ) throws IOException
    {
        // Parse the date string (yyyy-MM-dd) into a LocalDate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date, formatter);
        
        // Convert LocalDate to LocalDateTime at start of day (midnight)
        Timestamp tanggalSetlist = Timestamp.valueOf(localDate.atStartOfDay());
        //set timestamp
        LocalDateTime currentDateTime = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(currentDateTime);

        //save bukti
        Setlist setlist = setlistService.findByIdSetlist(idSetlist).get();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String formattedTimestamp = dateFormat.format(new Date(timestamp.getTime()));
        String namaImage = (setlist.getNamaSetlist() + formattedTimestamp).replaceAll("\\s+", "");
        String path = saveImage("bukti", file,namaImage);

        Artis artis = artisService.findByIdArtis(setlist.getIdArtis()).get(0);
        String namaSetlist = artis.getNamaArtis() + " at " + namaLokasi;
        Lokasi lokasi = lokasiService.findByNamaLokasi(namaLokasi).get();

        Iterable<Show> listShow = showService.findByNamaShow(namaShow);
        Iterator<Show> iterator = listShow.iterator();
        Show show = iterator.hasNext()? iterator.next() : null;

        String email = (String) session.getAttribute("email");
        setlistService.updateSetlist(namaSetlist, idSetlist, tanggalSetlist, lokasi.getIdLokasi(), path, show.getIdShow(), email, timestamp);

        return "";
    }

    @GetMapping("/edit/setlistSongs/{idSetlist}")
    public String editSetlistSongs(
        @PathVariable int idSetlist, 
        Model model, HttpSession session)
    {
        Optional<Setlist> optionalSetlist = setlistService.findByIdSetlist(idSetlist);
        if(optionalSetlist.isPresent()){
            Setlist setlist = optionalSetlist.get();
            List<SetlistSong> setlistSong = setlistService.findSetlistSongByIdSetlist(setlist.getIdSetlist());
            model.addAttribute("listLagu", setlistSong);
            model.addAttribute("idSetlist", setlist.getIdSetlist());
        }

        Iterable<Lagu> listLagu = laguService.findByNamaLagu("");
        model.addAttribute("listLaguSemua", listLagu);


        if(session.getAttribute("username") == null){
            model.addAttribute("isUserLoggedIn", false);
        }
        else{
            model.addAttribute("isUserLoggedIn", true);
        }
        if (session.getAttribute("username") == null) {
            return "redirect:/login";
        } else {
            return "editSetlist";
        }
    }

    @PostMapping("/edit/setlistSongs/{idSetlist}")
    public String editSetlistSongsPost(
        @PathVariable int idSetlist, 
        @RequestParam("songNames") List<String> listLagu,
        @RequestParam("file") MultipartFile file,
        Model model, HttpSession session) throws IOException
    {
        //set timestamp
        LocalDateTime currentDateTime = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(currentDateTime);

        //save bukti
        Setlist setlist = setlistService.findByIdSetlist(idSetlist).get();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String formattedTimestamp = dateFormat.format(new Date(timestamp.getTime()));
        String namaImage = (setlist.getNamaSetlist() + formattedTimestamp).replaceAll("\\s+", "");
        String path = saveImage("bukti", file,namaImage);

        String email = (String) session.getAttribute("email");
        String tes = "";
        List<SetlistSong> listLaguOld = setlistService.findSetlistSongByIdSetlist(idSetlist);
        int i;
        for(i = 0;i < listLagu.size() && i < listLaguOld.size();i++){
            Iterable<Lagu> iterableLagu = laguService.findByNamaLagu(listLagu.get(i));
            Iterator<Lagu> iterator = iterableLagu.iterator();
            Lagu lagu = iterator.hasNext()? iterator.next() : null;

            SetlistSong laguOld = listLaguOld.get(i);
            if(lagu.getNamaLagu().equals(laguOld.getNamaLagu()) == false){       //kalo beda
                setlistService.changeSong(idSetlist, lagu.getIdLagu(), laguOld.getTrackNumber(), email, timestamp, path);
            }
        }

        //kalo jumlah berkurang
        if(listLagu.size() < listLaguOld.size()){
            while(i < listLaguOld.size()){
                SetlistSong laguOld = listLaguOld.get(i);
                setlistService.removeSongFromSetlist(idSetlist, laguOld.getIdLagu(), email,laguOld.getTrackNumber(), timestamp, path);
                i++;
            }
        }
        else{   //kalo jumlah bertambah
            while(i < listLagu.size()){
                Iterable<Lagu> iterableLagu = laguService.findByNamaLagu(listLagu.get(i));
                Iterator<Lagu> iterator = iterableLagu.iterator();
                Lagu lagu = iterator.hasNext()? iterator.next() : null;

                setlistService.addSongToSetlist(idSetlist, lagu.getIdLagu(), email, timestamp,path);
                i++;
            }
        }

        if(session.getAttribute("username") == null){
            model.addAttribute("isUserLoggedIn", false);
        }
        else{
            model.addAttribute("isUserLoggedIn", true);
        }
        return "redirect:/edit/setlistSongs/" + idSetlist;
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

    @PostMapping("/addPenggunaSetlist")
    public String addPenggunaSetlist(
        @RequestParam("idSetlist")String idSetlistBef,
        @RequestParam("namaSetlist")String namaSetlist,
    HttpSession session)
    {
        int idSetlist = Integer.parseInt(idSetlistBef);
        String username = (String)session.getAttribute("username");
        if(username == null){
            return "redirect:/login";
        }
        else{
            String email = (String)session.getAttribute("email");
            PenggunaSetlist ps = userService.findInSetlist(email, idSetlist);
            if(ps == null){
                userService.addToPenggunaSetlist(email, idSetlist);
            }
            else{
                userService.removeFromPenggunaSetlist(email, idSetlist);
            }
            return "redirect:/setlist/" + namaSetlist + "-" + idSetlist;
        }
    }

    public String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0) {
            return fileName.substring(dotIndex);
        } else {
            return ""; 
        }
    }

    @RestController
    @RequestMapping("/get")
    public class ShowController {

        @GetMapping("/showDetails")
        public Show getShowDetails(@RequestParam String namaShow) {
            Iterable<Show> listShow = showService.findByNamaShow(namaShow);
            Iterator<Show> iterator = listShow.iterator();
            Show show = iterator.hasNext()? iterator.next() : null;
            return show;
        }
    }
}
