package com.serjeshs.usersvk.service;

import com.github.scribejava.apis.VkontakteApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.serjeshs.usersvk.domain.User;
import com.serjeshs.usersvk.dto.UsersControllDto;
import com.serjeshs.usersvk.repository.UserRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Service
public class VkAuthServiceImpl implements VkAuthService {

    @Value("${vkontakte.clientId}")
    private String clientId;
    @Value("${vkontakte.clientSecret}")
    private String clientSecret;
    @Value("${vkontakte.callbackUri}")
    private String redirectCallBackUri;
    @Value("${vkontakte.scope}")
    private String scope;

    private final UsersService usersService;
    private final UserRepository userRepository;

    private final Logger logger = LoggerFactory.getLogger("VKAuth Service");

    private static final String PROTECTED_RESOURCE_URL = "https://api.vk.com/method/users.get?v="
            + VkontakteApi.VERSION;

    public VkAuthServiceImpl(UsersService usersService, UserRepository userRepository) {
        this.usersService = usersService;
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2AccessToken handleCallback(String code) throws InterruptedException, ExecutionException, IOException, JSONException {

        final OAuth20Service service = new ServiceBuilder(clientId)
                .apiSecret(clientSecret)
                .scope(scope)
                .callback(redirectCallBackUri)
                .build(VkontakteApi.instance());
        final OAuth2AccessToken accessToken = service.getAccessToken(code);

        final OAuthRequest oauthRequest = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
        service.signRequest(accessToken, oauthRequest);

        final Response resourceResponse = service.execute(oauthRequest);

        JSONObject jsonObject = new JSONObject(resourceResponse.getBody());

        JSONArray responseArray = jsonObject.getJSONArray("response");
        JSONObject obj = responseArray.getJSONObject(0);

        System.out.println(obj.toString());
        String userId = "";
        String firstName = "";
        String lastName = "";
        try {
            userId = obj.getString("id");
            firstName = obj.getString("first_name");
            lastName = obj.getString("last_name");
            if (!userRepository.findByName(userId).isPresent()) {
                UUID uuid = UUID.randomUUID();
                System.out.println("пароль для нового пользователя: " + uuid.toString());
                usersService.addUser(new UsersControllDto(firstName + lastName, userId, uuid.toString(), User.ROLE_USER));
            }
        } catch (Exception e) {
            System.out.println("Не удалось вытащить данные из json");
        }

        System.out.println("userId : " + userId + "firstName : " + firstName + "lastName : " + lastName);

        return accessToken;

    }
}
