package com.recipeapp.plip.plipapp.service;

import org.apache.commons.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * A simple class for executing http calls against a remote API
 */
public class HttpRequestManager {
    private final String GET = "GET";

    public String getRecipe(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection httpClient = (HttpURLConnection) url.openConnection();
        httpClient.setRequestMethod(GET);

        InputStream stream = new BufferedInputStream(httpClient.getInputStream());
        return IOUtils.toString(stream);
    }
}