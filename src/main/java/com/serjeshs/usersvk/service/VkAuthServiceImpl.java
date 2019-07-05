package com.serjeshs.usersvk.service;

import com.github.scribejava.apis.VkontakteApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.serjeshs.usersvk.domain.User;
import com.serjeshs.usersvk.dto.UserDto;
import com.serjeshs.usersvk.repository.UserRepository;
import com.serjeshs.usersvk.security.CustomAuthenticationManager;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Service
public class VkAuthServiceImpl implements VkAuthService {

    @Value("${vkontakte.clientId}")
    private String clientId;
    @Value("${vkontakte.clientCode}")
    private String clientCode;
    @Value("${vkontakte.callbackUri}")
    private String redirectCallBackUri;
    @Value("${vkontakte.scope}")
    private String scope;

    private static final String PROTECTED_RESOURCE_URL = "https://api.vk.com/method/users.get?v=" + VkontakteApi.VERSION;
    private final Logger logger = LoggerFactory.getLogger("VKAuth Service");

    private final UsersService usersService;
    private final UserRepository userRepository;

    public VkAuthServiceImpl(UsersService usersService, UserRepository userRepository) {
        this.usersService = usersService;
        this.userRepository = userRepository;
    }

    @Override
    public void handleCallback(String code) throws InterruptedException, ExecutionException, IOException, JSONException {

        OAuth20Service service = new ServiceBuilder(clientId)
                .apiSecret(clientCode)
                .scope(scope)
                .callback(redirectCallBackUri)
                .build(VkontakteApi.instance());
        OAuth2AccessToken accessToken = service.getAccessToken(code);
        OAuthRequest oauthRequest = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
        service.signRequest(accessToken, oauthRequest);
        Response resourceResponse = service.execute(oauthRequest);
        JSONObject jsonObject = new JSONObject(resourceResponse.getBody());
        JSONArray responseArray = jsonObject.getJSONArray("response");
        JSONObject obj = responseArray.getJSONObject(0);
        String userId = "";
        String firstName = "";
        String lastName = "";
        try {
            userId = obj.getString("id");
            firstName = obj.getString("first_name");
            lastName = obj.getString("last_name");
            String password;
            Optional<User> optionalUser = userRepository.findByName("id" + userId);

            if (!optionalUser.isPresent()) {
                UUID uuid = UUID.randomUUID();
                usersService.addUser(new UserDto(firstName + " " + lastName, "id" + userId, uuid.toString(), User.ROLE_USER));
                password = new BCryptPasswordEncoder().encode(uuid.toString());
            } else {
                password = optionalUser.get().getPassword();
            }
            AuthenticationManager am = new CustomAuthenticationManager();
            try {
                Authentication request = new UsernamePasswordAuthenticationToken("id" + userId, password);
                Authentication result = am.authenticate(request);
                SecurityContextHolder.getContext().setAuthentication(result);
            } catch (AuthenticationException e) {
                logger.error("Authentication failed");
            }
        } catch (Exception e) {
            logger.error("Wrong json");
        }

    }

    @Override
    public String prepareRedirectUrl() {
        final OAuth20Service service = new ServiceBuilder(clientId)
                .apiSecret(clientCode)
                .scope(scope)
                .callback(redirectCallBackUri)
                .build(VkontakteApi.instance());
        return service.getAuthorizationUrl();
    }
}
