package com.axreng.backend.services;

import com.axreng.backend.domain.CrawlResult;
import com.axreng.backend.repositories.CrawlRepository;
import com.axreng.backend.util.MyRequest;
import com.axreng.backend.util.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CrawlService {
    private final CrawlRepository crawlRepository;
    private final MyRequest myRequest;
    private final Util util;

    public CrawlService(CrawlRepository crawlRepository, MyRequest myRequest,Util util) {
        this.crawlRepository = crawlRepository;
        this.myRequest = myRequest;
        this.util = util;
    }

    public List<String> initiateCrawl(String keyword) {
        if (isValidKeyword(keyword)) {
            List<String> searchIds = new ArrayList<>();

            for (String kw : keyword.split(",")) {
                String searchId = util.generateID();

                CrawlResult crawlResult = new CrawlResult(kw);
                crawlResult.setSearchId(searchId);

                List<String> foundIds = myRequest.sendPostRequest(keyword);

                crawlRepository.saveCrawlResult(searchId, crawlResult);

                searchIds.addAll(foundIds);
            }
            return searchIds;
        } else {
            throw new IllegalArgumentException("O termo fornecido não atende aos requisitos.");
        }
    }

    public CrawlResult getCrawlResult(String searchId, String baseUrl) {
        CrawlResult crawlResult = crawlRepository.getCrawlResult(searchId);

        if (crawlResult != null) {
            if ("active".equals(crawlResult.getStatus())) {
                List<String> foundUrls = crawlWebsite(baseUrl);
                crawlResult.setUrls(foundUrls);
                crawlResult.setStatus("done");
            }

            return crawlResult;
        } else {
            throw new RuntimeException("Search ID not found");
        }
    }

    private List<String> crawlWebsite(String baseUrl) {
        List<String> foundUrls = new ArrayList<>();

        try {
            URL url = new URL(baseUrl); // Use a baseUrl fornecida como argumento
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                String htmlContent = response.toString();
                foundUrls.addAll(findUrlsWithKeyword(htmlContent, baseUrl));
            } else {
                System.out.println("Falha ao conectar ao site. Código de resposta: " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return foundUrls;
    }

    public List<String> findUrlsWithKeyword(String htmlContent, String baseUrl) {
        List<String> urls = new ArrayList<>();
        Pattern pattern = Pattern.compile("<a\\s+(?:[^>]*?\\s+)?href=([\"'])(.*?)\\1");

        Matcher matcher = pattern.matcher(htmlContent);
        while (matcher.find()) {
            String url = matcher.group(2);
            if (url.contains(baseUrl)) {
                urls.add(url);
            }
        }

        return urls;
    }

    private boolean isValidKeyword(String keyword) {
        return keyword != null && keyword.length() >= 4 && keyword.length() <= 32;
    }
}
