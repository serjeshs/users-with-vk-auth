package com.serjeshs.usersvk.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
public class PagesController {
    @Value("${application.version}")
    private String ver;

    private final Logger logger = LoggerFactory.getLogger("Pages Controller");

    @GetMapping("/login")
    public String loginPage(Model model) {
        return "login";
    }

    @GetMapping(value = {"/", "/index.html", "/index"})
    public String indexPage(Model model, HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        model.addAttribute("username", principal.getName());
        model.addAttribute("appversion", ver);
        return "index";
    }

    @GetMapping("/user-management")
    public String userManagementPage(Model model, HttpServletRequest request) {
        model.addAttribute("appversion", ver);
        Principal principal = request.getUserPrincipal();
        model.addAttribute("username", principal.getName());
        return "user-management";
    }
}
