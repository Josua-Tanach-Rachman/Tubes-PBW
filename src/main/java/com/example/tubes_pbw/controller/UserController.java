package com.example.tubes_pbw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.tubes_pbw.model.artis.ArtisService;
import com.example.tubes_pbw.model.artis.ArtisSetlistCountDTO;
import com.example.tubes_pbw.model.user.User;
import com.example.tubes_pbw.model.user.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private ArtisService artisService;

    @GetMapping("/login")
    public String loginView(HttpSession session) {
        if (session.getAttribute("username") == null) {
            return "login";
        }
        if(session.getAttribute("role").equals("admin")){
            return "redirect:/admin";
        }
        if(session.getAttribute("role").equals("user")){
            return "redirect:/public";
        }
        return "redirect:/";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password,HttpSession session, Model model) {
        User user = userService.login(username, password);
        if(user ==null){
            model.addAttribute("status", "");
            return "login";
        }

        session.setAttribute("username", user.getUsername());
        session.setAttribute("role", user.getRole());
        return "redirect:/login";
    }

    @GetMapping("/register")
    public String registerView(User user){
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid User user, BindingResult bindingResult, @RequestParam("confirmpassword") String confirmPassword, RedirectAttributes redirectAttrs, Model model){
        model.addAttribute("user", user);
        if(bindingResult.hasErrors()){
            // redirectAttrs.addFlashAttribute("error","");
            return "redirect:/register";
        }
        
        if(!user.getPassword().equals(confirmPassword)){
            bindingResult.rejectValue(
                "password",
                "PasswordMismatch",
                "Passwords do not match"
            );
            return "register";
        }
        boolean status = userService.register(user);
        model.addAttribute("error",user);
        if(status){
            return "redirect:/results";
        }
        else{
            bindingResult.rejectValue("username"
            ,"INVALID Username","INVALID Username");
            return "register";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/setlist")
    public String setlist(User user){
        return "setlist";
    }

    @GetMapping("/artist")
    public String artist(
        @RequestParam(required = false, defaultValue = "1") String page,
        @RequestParam(required = false, defaultValue = "") String filter, 
        Model model)
    {
        int curPage = Integer.parseInt(page);
        
        long count = artisService.countByFilterNamaArtis(filter);

        long max = artisService.maxSetlistCountForArtis();

        Iterable<ArtisSetlistCountDTO> res = artisService.findByFilterNamaArtisWithOffsetReturnWithCount(filter,10, (curPage-1)*10);
        
        model.addAttribute("filter",filter);
        model.addAttribute("listArtis", res);
        model.addAttribute("max", max);
        model.addAttribute("kategori", "artist");
        model.addAttribute("pageCount",(int)Math.ceil((double)count/10));
        model.addAttribute("currentPage",curPage);
        return "artist";
    }

    @GetMapping("/concert")
    public String concert(User user){
        return "concert";
    }

    @GetMapping("/addsetlist")
    public String addsetlist(User user){
        return "addSetlist";
    }

    @GetMapping("/search")
    public String searchAll(@RequestParam(required = false, defaultValue = "") String filter ,User user, Model model){
        Iterable<ArtisSetlistCountDTO> res = artisService.findByFilterNamaArtisWithOffsetReturnWithCount(filter,5, (1-1)*10);
        long maxArtis = artisService.maxSetlistCountForArtis();
        model.addAttribute("filter", filter);
        model.addAttribute("maxArtis", maxArtis);
        model.addAttribute("listArtis", res);

        
        return "searchPage";
    }


    @GetMapping("/artistDetail")
    public String artistDetail(){
        return "artistDetail";
    }

    @GetMapping("/addArtist")
    public String addArtist(User user){
        return "addArtist";
    }

    @GetMapping("/addSong")
    public String addSong(User user){
        return "addsong";
    }

    @GetMapping("/addShow")
    public String addShow(User user){
        return "addShow";
    }
}