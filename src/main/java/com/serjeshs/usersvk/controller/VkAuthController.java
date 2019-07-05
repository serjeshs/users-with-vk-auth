package com.serjeshs.usersvk.controller;

import com.serjeshs.usersvk.service.VkAuthService;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

@Controller
public class VkAuthController {

    private final Logger logger = LoggerFactory.getLogger("VKAuth Controller");

    private final VkAuthService vkAuthService;

    public VkAuthController(VkAuthService vkAuthService) {
        this.vkAuthService = vkAuthService;
    }

    @RequestMapping(value = "/signin", method = RequestMethod.GET)
    public void vkLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("request to VK");
        response.sendRedirect(vkAuthService.prepareRedirectUrl());
    }

    @RequestMapping(value = "/callback", method = RequestMethod.GET)
    public String callback(@RequestParam(value = "code", required = false) String code,
                           @RequestParam(value = "state", required = false) String state,
                           HttpServletRequest request, ModelMap model) throws IOException, JSONException,
            ExecutionException, InterruptedException {
        logger.info("callback from VK");
        vkAuthService.handleCallback(code);
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("username", name);
        return "index";
    }
}