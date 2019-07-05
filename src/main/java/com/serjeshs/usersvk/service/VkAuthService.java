package com.serjeshs.usersvk.service;

import org.json.JSONException;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public interface VkAuthService {

    void handleCallback(String code) throws InterruptedException, ExecutionException, IOException, JSONException;

    String prepareRedirectUrl();
}
