package com.example.tubes_pbw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.tubes_pbw.model.user.User;
import com.example.tubes_pbw.model.user.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

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
    public String artist(User user){
        return "artist";
    }

    @GetMapping("/concert")
    public String concert(User user){
        return "concert";
    }
}