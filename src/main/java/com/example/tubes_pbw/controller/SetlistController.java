package com.example.tubes_pbw.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.tubes_pbw.model.lagu.Lagu;
import com.example.tubes_pbw.model.lagu.LaguService;
import com.example.tubes_pbw.model.negara.Negara;
import com.example.tubes_pbw.model.setlist.Setlist;
import com.example.tubes_pbw.model.setlist.SetlistService;
import com.example.tubes_pbw.model.setlist.SetlistSong;

import jakarta.servlet.http.HttpSession;

@Controller
public class SetlistController {

    @Autowired
    LaguService laguService;

    @Autowired
    SetlistService setlistService;

    // @GetMapping("/edit/setlistSongs/{idSetlist}")
    // public String editSetlistSongs(
    //     @PathVariable int idSetlist, 
    //     Model model, HttpSession session)
    // {
    //     Optional<Setlist> optionalSetlist = setlistService.findByIdSetlist(idSetlist);
    //     if(optionalSetlist.isPresent()){
    //         Setlist setlist = optionalSetlist.get();
    //         List<SetlistSong> setlistSong = setlistService.findSetlistSongByIdSetlist(setlist.getIdSetlist());
    //         model.addAttribute("listLagu", setlistSong);
    //         model.addAttribute("idSetlist", setlist.getIdSetlist());
    //     }

    //     Iterable<Lagu> listLagu = laguService.findByNamaLagu("");
    //     model.addAttribute("listLaguSemua", listLagu);


    //     if(session.getAttribute("username") == null){
    //         model.addAttribute("isUserLoggedIn", false);
    //     }
    //     else{
    //         model.addAttribute("isUserLoggedIn", true);
    //     }
    //     return "editSetlist";
    // }

    // @PostMapping("/edit/setlistSongs/{idSetlist}")
    // public String editSetlistSongsPost(
    //     @PathVariable int idSetlist, 
    //     @RequestParam("songNames") List<String> listLagu,
    //     @RequestParam("file") MultipartFile file,
    //     Model model, HttpSession session)
    // {
    //     //set timestamp
    //     LocalDateTime currentDateTime = LocalDateTime.now();
    //     Timestamp timestamp = Timestamp.valueOf(currentDateTime);

    //     //save bukti
    //     Setlist setlist = setlistService.findByIdSetlist(idSetlist).get();
    //     String namaImage = (setlist.getNamaSetlist() + timestamp.toString()).replaceAll("\\s+", "");
    //     // String path = saveImage("bukti", file,namaImage);

    //     String email = (String) session.getAttribute("email");
    //     String tes = "";
    //     List<SetlistSong> listLaguOld = setlistService.findSetlistSongByIdSetlist(idSetlist);
    //     int i;
    //     for(i = 0;i < listLagu.size() && i < listLaguOld.size();i++){
    //         Iterable<Lagu> iterableLagu = laguService.findByNamaLagu(listLagu.get(i));
    //         Iterator<Lagu> iterator = iterableLagu.iterator();
    //         Lagu lagu = iterator.hasNext()? iterator.next() : null;

    //         SetlistSong laguOld = listLaguOld.get(i);
    //         if(lagu.getNamaLagu().equals(laguOld.getNamaLagu()) == false){       //kalo beda
    //             setlistService.changeSong(idSetlist, lagu.getIdLagu(), laguOld.getTrackNumber(), email, timestamp);
    //         }
    //     }

    //     //kalo jumlah berkurang
    //     if(listLagu.size() < listLaguOld.size()){
    //         while(i < listLaguOld.size()){
    //             SetlistSong laguOld = listLaguOld.get(i);
    //             setlistService.removeSongFromSetlist(idSetlist, laguOld.getIdLagu(), email,laguOld.getTrackNumber(), timestamp);
    //             i++;
    //         }
    //     }
    //     else{   //kalo jumlah bertambah
    //         while(i < listLagu.size()){
    //             Iterable<Lagu> iterableLagu = laguService.findByNamaLagu(listLagu.get(i));
    //             Iterator<Lagu> iterator = iterableLagu.iterator();
    //             Lagu lagu = iterator.hasNext()? iterator.next() : null;

    //             setlistService.addSongToSetlist(idSetlist, lagu.getIdLagu(), email, timestamp);
    //         }
    //     }

    //     if(session.getAttribute("username") == null){
    //         model.addAttribute("isUserLoggedIn", false);
    //     }
    //     else{
    //         model.addAttribute("isUserLoggedIn", true);
    //     }
    //     return "redirect/edit/setlistSongs/" + idSetlist;
    // }
}
