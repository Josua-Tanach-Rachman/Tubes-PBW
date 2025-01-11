package com.example.tubes_pbw.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.tubes_pbw.model.album.Album;
import com.example.tubes_pbw.model.album.AlbumService;
import com.example.tubes_pbw.model.artis.Artis;
import com.example.tubes_pbw.model.artis.ArtisService;
import com.example.tubes_pbw.model.artis.ArtisSetlistCountDTO;
import com.example.tubes_pbw.model.komentar.KomentarPengguna;
import com.example.tubes_pbw.model.komentar.KomentarService;
import com.example.tubes_pbw.model.lagu.Lagu;
import com.example.tubes_pbw.model.lagu.LaguArtisAlbum;
import com.example.tubes_pbw.model.lagu.LaguJumlahSetlist;
import com.example.tubes_pbw.model.lagu.LaguService;
import com.example.tubes_pbw.model.lagu.LaguTanggalShow;
import com.example.tubes_pbw.model.user.PenggunaSetlist;
import com.example.tubes_pbw.model.user.User;
import com.example.tubes_pbw.model.user.UserService;
import com.example.tubes_pbw.model.setlist.ArtistSetlistLokasiDate;
import com.example.tubes_pbw.model.setlist.Setlist;
import com.example.tubes_pbw.model.setlist.SetlistDetail;
import com.example.tubes_pbw.model.setlist.SetlistJumlahPengguna;
import com.example.tubes_pbw.model.setlist.SetlistService;
import com.example.tubes_pbw.model.setlist.SetlistSong;
import com.example.tubes_pbw.model.setlistHistory.SetlistHistoryPengguna;
import com.example.tubes_pbw.model.setlistHistory.SetlistHistoryService;
import com.example.tubes_pbw.model.show.ShowJumlahPengguna;
import com.example.tubes_pbw.model.show.ShowService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private ArtisService artisService;

    @Autowired
    private SetlistService setlistService;

    @Autowired
    private ShowService showService;

    @Autowired
    private KomentarService komentarService;

    @Autowired
    private SetlistHistoryService setlistHistoryService;

    @Autowired
    private LaguService laguService;

    @Autowired
    private AlbumService albumService;

    @GetMapping("/login")
    public String loginView(HttpSession session) {
        if (session.getAttribute("username") == null) {
            return "login";
        }
        if (session.getAttribute("role").equals("admin")) {
            return "redirect:/manageUser";
        }
        if (session.getAttribute("role").equals("user")) {
            return "redirect:/public";
        }
        return "redirect:/";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session,
            Model model) {
        User user = userService.login(username, password);
        if (user == null) {
            model.addAttribute("status", "");
            return "login";
        }

        session.setAttribute("username", user.getUsername());
        session.setAttribute("email", user.getEmail());
        session.setAttribute("role", user.getRole());
        return "redirect:/login";
    }

    @GetMapping("/register")
    public String registerView(User user) {
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid User user, BindingResult bindingResult,
            @RequestParam("confirmpassword") String confirmPassword, RedirectAttributes redirectAttrs, Model model) {
        model.addAttribute("user", user);
        if (bindingResult.hasErrors()) {
            // redirectAttrs.addFlashAttribute("error","");
            return "redirect:/register";
        }

        if (!user.getPassword().equals(confirmPassword)) {
            bindingResult.rejectValue(
                    "password",
                    "PasswordMismatch",
                    "Passwords do not match");
            return "register";
        }
        boolean status = userService.register(user);
        model.addAttribute("error", user);
        if (status) {
            return "redirect:/login";
        } else {
            bindingResult.rejectValue("username", "INVALID Username", "INVALID Username");
            return "register";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/setlist")
    public String setlist(
            @RequestParam(required = false, defaultValue = "1") String page,
            @RequestParam(required = false, defaultValue = "") String filter,
            Model model,
            HttpSession session) {
        int curPage = Integer.parseInt(page);

        long count = setlistService.countByFilterNamaSetlist(filter);

        long max = setlistService.maxSetlistCountForEachSetlist();

        Iterable<SetlistJumlahPengguna> res = setlistService.findSetlistByFilterNamaWithOffsetReturnWithCount(filter,
                10, (curPage - 1) * 10);

        model.addAttribute("filter", filter);
        model.addAttribute("listSetlist", res);
        model.addAttribute("max", max);
        model.addAttribute("kategori", "setlist");
        model.addAttribute("pageCount", (int) Math.ceil((double) count / 10));
        model.addAttribute("currentPage", curPage);

        if (session.getAttribute("username") == null) {
            model.addAttribute("isUserLoggedIn", false);
        } else {
            model.addAttribute("isUserLoggedIn", true);
            if (session.getAttribute("role").equals("admin")) {
                model.addAttribute("isAdmin", true);
            } else {
                model.addAttribute("isAdmin", false);
            }
        }

        return "setlist";
    }

    @GetMapping("/artist")
    public String artist(
            @RequestParam(required = false, defaultValue = "1") String page,
            @RequestParam(required = false, defaultValue = "") String filter,
            Model model, HttpSession session) {
        int curPage = Integer.parseInt(page);

        long count = artisService.countByFilterNamaArtis(filter);

        long max = artisService.maxSetlistCountForArtis();

        Iterable<ArtisSetlistCountDTO> res = artisService.findByFilterNamaArtisWithOffsetReturnWithCount(filter, 10,
                (curPage - 1) * 10);

        model.addAttribute("filter", filter);
        model.addAttribute("listArtis", res);
        model.addAttribute("max", max);
        model.addAttribute("kategori", "artist");
        model.addAttribute("pageCount", (int) Math.ceil((double) count / 10));
        model.addAttribute("currentPage", curPage);

        if (session.getAttribute("username") == null) {
            model.addAttribute("isUserLoggedIn", false);
        } else {
            model.addAttribute("isUserLoggedIn", true);
            if (session.getAttribute("role").equals("admin")) {
                model.addAttribute("isAdmin", true);
            } else {
                model.addAttribute("isAdmin", false);
            }
        }
        return "artist";
    }

    @GetMapping("/concert")
    public String concert(
            @RequestParam(required = false, defaultValue = "1") String page,
            @RequestParam(required = false, defaultValue = "") String filter,
            User user, HttpSession session, Model model) {
        int curPage = Integer.parseInt(page);

        long count = showService.countByFilterNamaShow(filter);

        long max = showService.maxSetlistCountForEachShow();

        Iterable<ShowJumlahPengguna> res = showService.findShowByFilterNamaWithOffsetReturnWithCount(filter, 10,
                (curPage - 1) * 10);

        model.addAttribute("filter", filter);
        model.addAttribute("listShow", res);
        model.addAttribute("max", max);
        model.addAttribute("kategori", "concert");
        model.addAttribute("pageCount", (int) Math.ceil((double) count / 10));
        model.addAttribute("currentPage", curPage);

        if (session.getAttribute("username") == null) {
            model.addAttribute("isUserLoggedIn", false);
        } else {
            model.addAttribute("isUserLoggedIn", true);
            if (session.getAttribute("role").equals("admin")) {
                model.addAttribute("isAdmin", true);
            } else {
                model.addAttribute("isAdmin", false);
            }
        }

        return "concert";
    }

    @GetMapping("/addsetlist")
    public String addsetlist(User user, Model model, HttpSession session) {
        if(session.getAttribute("username") == null){
            model.addAttribute("isUserLoggedIn", false);
        }
        else{
            model.addAttribute("isUserLoggedIn", true);
            if(session.getAttribute("role").equals("admin")){
                model.addAttribute("isAdmin", true);
            } else {
                model.addAttribute("isAdmin", false);
            }
        }

        if (session.getAttribute("username") == null) {
            return "redirect:/login";
        } else {
            return "addSetlist";
        }
    }

    @GetMapping("/search")
    public String searchAll(@RequestParam(required = false, defaultValue = "") String filter, User user, Model model,
            HttpSession session) {
        Iterable<ArtisSetlistCountDTO> res = artisService.findByFilterNamaArtisWithOffsetReturnWithCount(filter, 5,
                (1 - 1) * 10);
        long maxArtis = artisService.maxSetlistCountForArtis();
        model.addAttribute("filter", filter);
        model.addAttribute("maxArtis", maxArtis);
        model.addAttribute("listArtis", res);

        Iterable<SetlistJumlahPengguna> resSetlist = setlistService
                .findSetlistByFilterNamaWithOffsetReturnWithCount(filter, 5, 0);
        long maxSetlist = setlistService.maxSetlistCountForEachSetlist();
        model.addAttribute("maxSetlist", maxSetlist);
        model.addAttribute("listSetlist", resSetlist);

        Iterable<ShowJumlahPengguna> resShow = showService.findShowByFilterNamaWithOffsetReturnWithCount(filter, 5, 0);
        long maxShow = showService.maxSetlistCountForEachShow();
        model.addAttribute("maxShow", maxShow);
        model.addAttribute("listShow", resShow);

        if(session.getAttribute("username") == null){
            model.addAttribute("isUserLoggedIn", false);
        }
        else{
            model.addAttribute("isUserLoggedIn", true);
            if(session.getAttribute("role").equals("admin")){
                model.addAttribute("isAdmin", true);
            } else {
                model.addAttribute("isAdmin", false);
            }
        }

        return "searchPage";
    }

    @GetMapping("/artist/{namaArtis}-{idArtis}")
    public String getArtistDetail(@PathVariable String namaArtis, @PathVariable int idArtis, Model model,
            HttpSession session) {
        List<Artis> artisList = artisService.findByIdArtis(idArtis);
        Artis artis = artisList.get(0);
        model.addAttribute("artis", artis);

        List<ArtistSetlistLokasiDate> lokasiDates = setlistService.findLokasiDate(idArtis);
        model.addAttribute("lokasiDates", lokasiDates);

        if(session.getAttribute("username") == null){
            model.addAttribute("isUserLoggedIn", false);
        }
        else{
            model.addAttribute("isUserLoggedIn", true);
            if(session.getAttribute("role").equals("admin")){
                model.addAttribute("isAdmin", true);
            } else {
                model.addAttribute("isAdmin", false);
            }
        }

        return "artistDetail";
    }

    @GetMapping("/addArtist")
    public String addArtist(User user, Model model, HttpSession session) {
        if(session.getAttribute("username") == null){
            model.addAttribute("isUserLoggedIn", false);
        }
        else{
            model.addAttribute("isUserLoggedIn", true);
            if(session.getAttribute("role").equals("admin")){
                model.addAttribute("isAdmin", true);
            } else {
                model.addAttribute("isAdmin", false);
            }
        }

        return "addArtist";
    }

    @GetMapping("/addSong")
    public String addSong(User user, HttpSession session, Model model) {
        if(session.getAttribute("username") == null){
            model.addAttribute("isUserLoggedIn", false);
        }
        else{
            model.addAttribute("isUserLoggedIn", true);
            if(session.getAttribute("role").equals("admin")){
                model.addAttribute("isAdmin", true);
            } else {
                model.addAttribute("isAdmin", false);
            }
        }
        return "addsong";
    }

    @GetMapping("/addConcert")
    public String addShow(User user, Model model, HttpSession session) {
        if(session.getAttribute("username") == null){
            model.addAttribute("isUserLoggedIn", false);
        }
        else{
            model.addAttribute("isUserLoggedIn", true);
            if(session.getAttribute("role").equals("admin")){
                model.addAttribute("isAdmin", true);
            } else {
                model.addAttribute("isAdmin", false);
            }
        }
        return "addShow";
    }

    @GetMapping("/setlist/{namaSetlist}-{idSetlist}")
    public String setlistDetail(@PathVariable String namaSetlist, @PathVariable int idSetlist, Model model,
            HttpSession session) {
        model.addAttribute("namaSetlist", namaSetlist);
        model.addAttribute("idSetlist", idSetlist);

        Optional<Setlist> optionalSetlist = setlistService.findByIdSetlist(idSetlist);
        if (optionalSetlist.isPresent()) {
            Setlist setlist = optionalSetlist.get();
            List<ArtistSetlistLokasiDate> generalInfo = setlistService
                    .findArtistSetlistLokasiDateByIdSetlist(setlist.getIdSetlist());

            model.addAttribute("generalInfo", generalInfo.get(0));

            List<SetlistSong> setlistSong = setlistService.findSetlistSongByIdSetlist(setlist.getIdSetlist());
            model.addAttribute("listLagu", setlistSong);

            // komentar
            Iterable<KomentarPengguna> listKomentarPengguna = komentarService
                    .findKomentarPenggunaBySetlistId(setlist.getIdSetlist());
            model.addAttribute("listKomentar", listKomentarPengguna);

            // setlist history
            Iterable<SetlistHistoryPengguna> listSetlistHistory = setlistHistoryService
                    .findAllByOrderByTanggalDiubahDesc(setlist.getIdSetlist());
            model.addAttribute("listHistory", listSetlistHistory);
        }

        if (session.getAttribute("username") == null) {
            model.addAttribute("isUserLoggedIn", false);
            model.addAttribute("terdaftar", false);
        } else {
            String email = (String) session.getAttribute("email");
            PenggunaSetlist ps = userService.findInSetlist(email, idSetlist);
            if (ps != null) {
                model.addAttribute("terdaftar", true);
            } else {
                model.addAttribute("terdaftar", false);
            }
            model.addAttribute("isUserLoggedIn", true);
            if(session.getAttribute("role").equals("admin")){
                model.addAttribute("isAdmin", true);
            } else {
                model.addAttribute("isAdmin", false);
            }
        }
        return "setlistDetail";
    }

    @GetMapping("/song")
    public String song(
            @RequestParam(required = false, defaultValue = "1") String page,
            @RequestParam(required = false, defaultValue = "") String filter,
            Model model,
            HttpSession session) {
        int curPage = Integer.parseInt(page);

        long count = laguService.countByFilterNamaLagu(filter);

        long max = laguService.maxSetlistCountForEachLagu();

        Iterable<LaguJumlahSetlist> res = laguService.findLaguWithLimitOffset(filter, 10, (curPage - 1) * 10);

        model.addAttribute("filter", filter);
        model.addAttribute("listLagu", res);
        model.addAttribute("max", max);
        model.addAttribute("kategori", "song");
        model.addAttribute("pageCount", (int) Math.ceil((double) count / 10));
        model.addAttribute("currentPage", curPage);

        if(session.getAttribute("username") == null){
            model.addAttribute("isUserLoggedIn", false);
        }
        else{
            model.addAttribute("isUserLoggedIn", true);
            if(session.getAttribute("role").equals("admin")){
                model.addAttribute("isAdmin", true);
            } else {
                model.addAttribute("isAdmin", false);
            }
        }

        return "songPage";
    }

    @GetMapping("/song/{namaSong}-{idSong}")
    public String songDetail(@PathVariable("namaSong") String namaLagu, @PathVariable("idSong") int idLagu,
            Model model, HttpSession session) {
        Optional<Lagu> LaguList = laguService.findByIdLagu(idLagu);
        Lagu Lagu = LaguList.get();
        model.addAttribute("Lagu", Lagu);

        List<LaguJumlahSetlist> laguSetlist = laguService.findLaguWithLimitOffset(Lagu.getNamaLagu(), 5, 0);
        model.addAttribute("jumlahDimainkan", laguSetlist.get(0));

        LaguArtisAlbum laguArtisAlbum = laguService.findLaguArtisAlbum(idLagu);
        model.addAttribute("laguArtisAlbum", laguArtisAlbum);

        List<LaguTanggalShow> tanggalShow = laguService.findTanggalShow(Lagu.getIdLagu());
        if (tanggalShow.size() >= 1) {
            model.addAttribute("pertama", tanggalShow.get(0));
            model.addAttribute("terakhir", tanggalShow.get(tanggalShow.size() - 1));
        } else {
            model.addAttribute("pertama", null);
            model.addAttribute("terakhir", null);
        }
        // List<LaguSetlistLokasiDate> lokasiDates =
        // setlistService.findLokasiDate(idLagu);
        // model.addAttribute("lokasiDates", lokasiDates);
        if(session.getAttribute("username") == null){
            model.addAttribute("isUserLoggedIn", false);
        }
        else{
            model.addAttribute("isUserLoggedIn", true);
            if(session.getAttribute("role").equals("admin")){
                model.addAttribute("isAdmin", true);
            } else {
                model.addAttribute("isAdmin", false);
            }
        }

        return "songDetail";
    }

    @GetMapping("/editSetlist")
    public String editSetlist(Model model, HttpSession session) {
        if (session.getAttribute("username") == null) {
            model.addAttribute("isUserLoggedIn", false);
        } else {
            model.addAttribute("isUserLoggedIn", true);
        }
        return "editSetlist";
    }

    @GetMapping("/aboutUs")
    public String aboutUs(Model model, HttpSession session) {
        if(session.getAttribute("username") == null){
            model.addAttribute("isUserLoggedIn", false);
        }
        else{
            model.addAttribute("isUserLoggedIn", true);
            if(session.getAttribute("role").equals("admin")){
                model.addAttribute("isAdmin", true);
            } else {
                model.addAttribute("isAdmin", false);
            }
        }
        return "aboutUs";
    }

    @GetMapping("/manageUser")
    public String manageUser(Model model, HttpSession session) {
        // Cek apakah user sudah login
        if (session.getAttribute("username") == null) {
            return "redirect:/login"; // Redirect ke login jika belum login
        }

        // Cek role user
        String role = (String) session.getAttribute("role");
        if (!"admin".equals(role)) {
            return "redirect:/"; // Redirect ke homepage jika bukan admin
        }

        List<User> listUsers = userService.findAllUsers(); // Asumsi ada service userService
        model.addAttribute("listUsers", listUsers);
        return "manageUser"; // Mengembalikan view manageUser.html
    }

    @PostMapping("/update-role")
    public ResponseEntity<String> updateUserRole(@RequestBody Map<String, String> payload) {
        String username = payload.get("userId");
        String newRole = payload.get("role");
        userService.updateUserRole(username, newRole);
        return ResponseEntity.ok("Role updated successfully");
    }

    @PostMapping("/update-status")
    public ResponseEntity<String> updateUserStatus(@RequestBody Map<String, String> payload) {
        String username = payload.get("userId");
        boolean newStatus = Boolean.parseBoolean(payload.get("status"));
        userService.updateUserStatus(username, newStatus);
        return ResponseEntity.ok("Status updated successfully");
    }

    @GetMapping("/report")
    public String report(Model model, HttpSession session) {
        // Cek apakah user sudah login
        if (session.getAttribute("username") == null) {
            return "redirect:/login"; // Redirect ke login jika belum login
        }

        // Cek role user
        String role = (String) session.getAttribute("role");
        if (!"admin".equals(role)) {
            return "redirect:/"; // Redirect ke homepage jika bukan admin
        }

        // Ambil data pengguna untuk ditampilkan di halaman
        List<User> listUsers = userService.findAllUsers(); // Asumsi ada service userService
        model.addAttribute("listUsers", listUsers);
        System.out.println("PANDA " + listUsers);
        return "report"; // Mengembalikan view manageUser.html
    }
}