package com.serjeshs.usersvk.service;

import com.github.scribejava.core.model.OAuth2AccessToken;
import org.json.JSONException;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public interface VkAuthService {
    OAuth2AccessToken handleCallback(String code) throws InterruptedException, ExecutionException, IOException, JSONException;
}
