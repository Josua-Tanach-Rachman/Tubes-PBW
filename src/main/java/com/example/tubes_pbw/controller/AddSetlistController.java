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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import com.example.tubes_pbw.model.album.Album;
import com.example.tubes_pbw.model.album.AlbumService;
import com.example.tubes_pbw.model.lagu.LaguService;
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
import com.example.tubes_pbw.model.detailHistory.DetailHistoryResponse;
import com.example.tubes_pbw.model.detailHistory.PairBefAfter;
import com.example.tubes_pbw.model.detailHistory.TrackBeforeAfter;
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
import com.example.tubes_pbw.model.setlistHistory.LaguNowBef;
import com.example.tubes_pbw.model.setlistHistory.SetlistHistory;
import com.example.tubes_pbw.model.setlistHistory.SetlistHistoryService;
import com.example.tubes_pbw.model.setlistHistory.SetlistNowBef;
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

    @Autowired
    SetlistHistoryService setlistHistoryService;

    @Autowired
    private AlbumService albumService;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    @GetMapping("/add/setlist/artist")
    @ResponseBody
    public Iterable<Artis> getAllArtis() {
        return artisService.findByFilterNamaArtis("");
    }

    @GetMapping("/add/song/album")
    @ResponseBody
    public Iterable<Album> getAllAlbumInArtist(@RequestParam("namaArtis") String namaArtis) {
        Optional<Artis> listArtis =  artisService.findByNamaArtis(namaArtis);
        int idArtis = listArtis.get().getIdArtis();
        return albumService.findByIdArtis(idArtis);
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

    @GetMapping("/add/setlist/album")
    @ResponseBody
    public Iterable<Album> getAllAlbum() {
        return albumService.findByFilterNamaAlbum("");
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
        Model model,User user, HttpSession session) throws IOException
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

        String email = (String) session.getAttribute("email");

        int idSetlist = setlistService.save(namaSetlist, timestamp, artis.getIdArtis(), lokasi.getIdLokasi(), path,show.getIdShow(), email);

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

    @PostMapping("/addSong")
    public String addSongToDb(
            @RequestParam("song-name") String namaSong,
            @RequestParam("album") String namaAlbum,
            @RequestParam("artist-name") String namaArtist,
            @RequestParam("photos") MultipartFile file
    ) throws IOException
    {
        String namaImage = namaSong.replaceAll("\\s+", "");
        String path = saveImage("lagu", file, namaImage);

        //mencari idAlbum
        List<Album> listAlbum = albumService.findByNamaAlbum(namaAlbum);
        int idAlbum = listAlbum.get(0).getIdAlbum();

        //mencari idArtis
        Optional<Artis> listArtis = artisService.findByNamaArtis(namaArtist);
        int idArtis = listArtis.get().getIdArtis();

        laguService.save(idAlbum, namaSong, 200, idArtis, path);

        return "redirect:/addSong";
    }

    @PostMapping("/addAlbum")
    public String addAlbumToDb(
            @RequestParam("artist-name") String namaArtis,
            @RequestParam("album-name") String namaAlbum,
            @RequestParam("release-date") Date releaseDate,
            @RequestParam("file") MultipartFile file) throws IOException
    {
        String namaImage = namaAlbum.replaceAll("\\s+", "");
        String path = saveImage("album", file, namaImage);

        Optional<Artis> artisList = artisService.findByNamaArtis(namaArtis);
        int idArtis = artisList.get().getIdArtis();

        albumService.save(namaAlbum, releaseDate, idArtis, path);
        return "redirect:/addAlbum";
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
            return "editSetlistInfo";
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

        Timestamp tanggalBef = Timestamp.valueOf(setlist.getTanggal());

        String email = (String) session.getAttribute("email");
        setlistService.updateSetlist(namaSetlist, idSetlist, tanggalSetlist, lokasi.getIdLokasi(), path, show.getIdShow(), email, timestamp, setlist.getIdLokasi(), setlist.getIdShow(), tanggalBef, setlist.getNamaSetlist());

        return "redirect:/setlist/" +
                setlist.getNamaSetlist().replace(" ", "-") +
                "-" +
                setlist.getIdSetlist();
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
                setlistService.changeSong(idSetlist, lagu.getIdLagu(), laguOld.getTrackNumber(), email, timestamp, path, laguOld.getIdLagu());
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
        return "redirect:/setlist/" + setlist.getNamaSetlist().replace(' ', '-') +"-"+ idSetlist;
    }

   @GetMapping("/setlistHistory/{idSetlist}/{date}")
    @ResponseBody
    public DetailHistoryResponse getDetailHistory(     //tracknumber, old, new
        @PathVariable int idSetlist, 
        @PathVariable String date, 
        Model model, HttpSession session
    )
    {
        DetailHistoryResponse detailHistoryResponse = new DetailHistoryResponse();
        List<TrackBeforeAfter> listTrackBeforeAfter = new ArrayList<>();
        List<PairBefAfter> lokasiShowTanggal = new ArrayList<>();

        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
        
        // Parse the string into a LocalDateTime
        LocalDateTime localDateTime = LocalDateTime.parse(date, formatter);
        
        // Convert LocalDateTime to Timestamp
        Timestamp timestamp = Timestamp.valueOf(localDateTime);
        
        SetlistHistory sh = setlistHistoryService.findSetlistHistory(idSetlist, timestamp).get(0);
        detailHistoryResponse.setUrlBukti(sh.getUrlBukti());

        List<LaguNowBef> listLaguYangDiubah = setlistHistoryService.findLaguBefAfter(idSetlist, timestamp);
        int i;
        String teks = "";
        //update info berarti
        if(listLaguYangDiubah.size() == 0){
            SetlistNowBef setlistNowBefs = setlistHistoryService.findSetlistNowBef(idSetlist, timestamp).get(0);    //bug disini karena getIdLokasiBef == null
            // if(setlistNowBefs.getIdLokasi() != setlistNowBefs.getIdLokasiBef()){
            if(setlistNowBefs.getIdLokasi().equals(setlistNowBefs.getIdLokasiBef()) == false){
                Lokasi bef;
                PairBefAfter pba = new PairBefAfter();
                if(setlistNowBefs.getIdLokasiBef() != null){
                    bef = lokasiService.findByIdLokasi(setlistNowBefs.getIdLokasiBef()).get(0);
                    // bef = null;
                    pba.setBefore(bef.getNamaLokasi());
                }
                else{
                    bef = null;
                }
                Lokasi after = lokasiService.findByIdLokasi(setlistNowBefs.getIdLokasi()).get(0);
                // PairBefAfter pba = new PairBefAfter();
                // pba.setBefore(bef.getNamaLokasi());
                pba.setAfter(after.getNamaLokasi());
                pba.setKategori("Location");
                lokasiShowTanggal.add(pba);
            }
            // if(setlistNowBefs.getIdShow() != setlistNowBefs.getIdShowBef()){
            if(setlistNowBefs.getIdShow().equals(setlistNowBefs.getIdShowBef()) == false){
                Show bef;
                PairBefAfter pba = new PairBefAfter();
                if(setlistNowBefs.getIdShowBef() != null){
                    bef = showService.findByIdShow(setlistNowBefs.getIdShowBef()).get();
                    // pba.setBefore(null);
                    pba.setBefore(bef.getNamaShow());
                }
                else{
                    bef = null;
                }
                Show after = showService.findByIdShow(setlistNowBefs.getIdShow()).get();
                // PairBefAfter pba = new PairBefAfter();
                // pba.setBefore(bef.getNamaShow());
                pba.setAfter(after.getNamaShow());
                pba.setKategori("Concert");
                lokasiShowTanggal.add(pba);
            }
            if(setlistNowBefs.getTanggalBef() == null){
                PairBefAfter pba = new PairBefAfter();
                pba.setBefore(null);
                pba.setAfter(setlistNowBefs.getTanggal().toString());
                pba.setKategori("Date");
                lokasiShowTanggal.add(pba);
            }
            else{
                int comparisonResult = setlistNowBefs.getTanggal().compareTo(setlistNowBefs.getTanggalBef());
                if(comparisonResult != 0){
                    PairBefAfter pba = new PairBefAfter();
                    pba.setBefore(setlistNowBefs.getTanggalBef().toString());
                    pba.setAfter(setlistNowBefs.getTanggal().toString());
                    pba.setKategori("tanggal");
                    lokasiShowTanggal.add(pba);
                }
            }
            detailHistoryResponse.setKategori("info");
            detailHistoryResponse.setLokasiShowTanggal(lokasiShowTanggal);
            return detailHistoryResponse;
        }

        for(i = 0;i < listLaguYangDiubah.size();i++){
            LaguNowBef laguNowBef = listLaguYangDiubah.get(i);
            if(laguNowBef.getAction().equals("INSERT")){
                Lagu laguAft = laguService.findByIdLagu(laguNowBef.getIdLagu()).get();

                TrackBeforeAfter trackBeforeAfter = new TrackBeforeAfter();
                trackBeforeAfter.setTracknumber(laguNowBef.getTrackNumber());
                trackBeforeAfter.setNamaLaguSebelumnya(null);
                trackBeforeAfter.setNamaLaguSetelahnya(laguAft.getNamaLagu());
                listTrackBeforeAfter.add(trackBeforeAfter);
            }   
            else if(laguNowBef.getAction().equals("DELETE")) {
                Lagu laguBef = laguService.findByIdLagu(laguNowBef.getIdLaguBef()).get();

                TrackBeforeAfter trackBeforeAfter = new TrackBeforeAfter();
                trackBeforeAfter.setTracknumber(laguNowBef.getTrackNumber());
                trackBeforeAfter.setNamaLaguSebelumnya(laguBef.getNamaLagu());
                trackBeforeAfter.setNamaLaguSetelahnya(null);
                listTrackBeforeAfter.add(trackBeforeAfter);
            }
            else{
                Lagu laguAft = laguService.findByIdLagu(laguNowBef.getIdLagu()).get();
                Lagu laguBef = laguService.findByIdLagu(laguNowBef.getIdLaguBef()).get();
                TrackBeforeAfter trackBeforeAfter = new TrackBeforeAfter();
                trackBeforeAfter.setTracknumber(laguNowBef.getTrackNumber());
                trackBeforeAfter.setNamaLaguSebelumnya(laguBef.getNamaLagu());
                trackBeforeAfter.setNamaLaguSetelahnya(laguAft.getNamaLagu());
                listTrackBeforeAfter.add(trackBeforeAfter);
            }
        }
        // teks = "hai";+
        detailHistoryResponse.setKategori("lagu");
        detailHistoryResponse.setTrackBeforeAfterList(listTrackBeforeAfter);
        return detailHistoryResponse;
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
