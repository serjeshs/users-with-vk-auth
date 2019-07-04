package com.serjeshs.usersvk.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
public class PagesController {
    @Value("${application.version}")
    private String ver;

    private final Logger logger = LoggerFactory.getLogger("Pages Controller");

    @GetMapping("/login")
//    @GetMapping(value = {"/", "/login"})
    public String loginPage(Model model) {
//        model.addAttribute("appversion", ver);
        return "login";
    }

    @GetMapping(value = {"/", "/index.html", "/index"})
//    @GetMapping(value = {"/index.html", "/index"})
    public String indexPage(Model model, HttpServletRequest request) {
        logger.info("IndexPage");
        Principal principal = request.getUserPrincipal();
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        model.addAttribute("username", username);
        model.addAttribute("username", principal.getName());
        model.addAttribute("appversion", ver);
        return "index";
    }

    @GetMapping("/registration")
    public String registrationPage(
            @RequestParam(value = "name",  required = false) String name,
            @RequestParam(value = "last_name",  required = false) String lastName,
            ModelMap model
    ) {
        logger.info("Registration");
        model.addAttribute("name",name);
        model.addAttribute("lastName",lastName);
        return "registration";
    }

    @GetMapping("/users-controll")
    public String usersControllPage(Model model, HttpServletRequest request) {
        model.addAttribute("appversion", ver);
        Principal principal = request.getUserPrincipal();
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        model.addAttribute("username", username);
        model.addAttribute("username", principal.getName());
        return "users-controll";
    }
}
