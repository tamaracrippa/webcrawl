package com.axreng.backend.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MyRequest {

    private String keyword;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public List<String> sendPostRequest(String keyword) {
        List<String> foundIds = new ArrayList<>();

        try {
            // Defina a URL para a qual você deseja enviar a requisição POST
            String postUrl = "http://hiring.axreng.com/";

            URL url = new URL(postUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            String requestBody = "{\"keyword\":\"" + keyword + "\"}";

            conn.getOutputStream().write(requestBody.getBytes());

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;

                while ((line = reader.readLine()) != null) {

                    if (line.contains("\"foundIds\":")) {
                        int startIndex = line.indexOf("[");
                        int endIndex = line.indexOf("]");
                        String foundIdsStr = line.substring(startIndex + 1, endIndex);
                        String[] foundIdsArray = foundIdsStr.split(",");
                        for (String foundId : foundIdsArray) {
                            foundIds.add(foundId.trim().replace("\"", ""));
                        }
                    }
                }
            } else {
                System.out.println("Falha ao conectar ao site. Código de resposta: " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return foundIds;
    }
}
