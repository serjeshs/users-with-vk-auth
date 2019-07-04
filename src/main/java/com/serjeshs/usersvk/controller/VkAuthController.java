package com.serjeshs.usersvk.controller;

import com.github.scribejava.apis.VkontakteApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.serjeshs.usersvk.service.VkAuthService;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.concurrent.ExecutionException;

@Controller
public class VkAuthController {
    @Value("${vkontakte.clientId}")
    private String clientId;
    @Value("${vkontakte.clientSecret}")
    private String clientSecret;
    @Value("${vkontakte.callbackUri}")
    private String redirectCallBackUri;
    @Value("${vkontakte.scope}")
    private String scope;

    private final VkAuthService vkAuthService;

    private final Logger logger = LoggerFactory.getLogger("VKAuth Controller");

    public VkAuthController(VkAuthService vkAuthService) {
        this.vkAuthService = vkAuthService;
    }

//    private static final String PROTECTED_RESOURCE_URL = "https://api.vk.com/method/users.get?v="
//            + VkontakteApi.VERSION;

    @RequestMapping(value = "/signin", method = RequestMethod.GET)
    public void vkLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final OAuth20Service service = new ServiceBuilder(clientId)
                .apiSecret(clientSecret)
                .scope(scope)
                .callback(redirectCallBackUri)
                .build(VkontakteApi.instance());
        final String redirectURL = service.getAuthorizationUrl();
        response.sendRedirect(redirectURL);
        logger.info("request to VK");
//        System.out.println("отправили запрос в vk");
    }

    @RequestMapping(value = "/callback", method = RequestMethod.GET)
    public String callback(@RequestParam(value = "code", required = false) String code,
                           @RequestParam(value = "state", required = false) String state,
                           HttpServletRequest request, ModelMap model) throws IOException, JSONException,
            ExecutionException, InterruptedException {

//        System.out.println("callback!!!");
        logger.info("callback from VK");
        OAuth2AccessToken accessToken = vkAuthService.handleCallback(code);

//        final OAuth20Service service = new ServiceBuilder(clientId)
//                .apiSecret(clientSecret)
//                .scope(scope)
//                .callback(redirectCallBackUri)
//                .build(VkontakteApi.instance());
//        final OAuth2AccessToken accessToken = service.getAccessToken(code);
//
//        final OAuthRequest oauthRequest = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
//        service.signRequest(accessToken, oauthRequest);
//
//        final Response resourceResponse = service.execute(oauthRequest);
//
//        JSONObject jsonObject = new JSONObject(resourceResponse.getBody());
//
//        JSONArray responseArray = jsonObject.getJSONArray("response");
//        JSONObject obj = responseArray.getJSONObject(0);
//
//        System.out.println(obj.toString());
//        String userId = "";
//        String firstName = "";
//        String lastName = "";
//        try {
//            userId = obj.getString("id");
//            firstName = obj.getString("first_name");
//            lastName = obj.getString("last_name");
//        } catch (Exception e) {
//            System.out.println("Не удалось вытащить данные из json");
//        }
//
//        System.out.println("userId : " + userId + "firstName : " + firstName + "lastName : " + lastName);



//        if (userService.findOne(Long.parseLong(userId)) != null) {
//            request.getSession().setAttribute("VK_ACCESS_TOKEN", accessToken);
//            model.addAttribute("user", userService.findOne(Long.parseLong(userId)));
//            final Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            if (user instanceof User) {
//                return "account";
//            } else {
//                return "/personalarea";
//            }
//        } else {
//            final User user = new User();
////            user.setFirst_name(first_name);
//            user.setFirstName(first_name);
//            user.setLastName(last_name);
//            model.addAttribute("user", user);
//            return "/registration";
//        }


//        model.addAttribute("name", firstName);
//        model.addAttribute("lastName", lastName);
//        return "registration";
//        model.addAttribute("user", userService.findOne(Long.parseLong(userId)));
//        request.getSession().setAttribute("VK_ACCESS_TOKEN", accessToken);
//        Principal principal = request.getUserPrincipal();
        String name = "blank";
        if(SecurityContextHolder.getContext().getAuthentication() != null) {
            name = SecurityContextHolder.getContext().getAuthentication().getName();
        }
        model.addAttribute("username", name);
        return "index";
//        return "redirect:/index";


//        return "redirect:/registration?name=" + firstName + "&last_name" + lastName;
    }
}