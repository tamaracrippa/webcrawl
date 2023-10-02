package com.axreng.backend.domain;

import java.util.ArrayList;
import java.util.List;

public class CrawlResult {
    private String id;
    private String status;
    private List<String> urls;

    public CrawlResult(String id) {
        this.id = id;
        this.status = "active";
        this.urls = new ArrayList<>();
    }
    public void setSearchId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    // Método para adicionar uma URL à lista de URLs
    public void addUrl(String url) {
        this.urls.add(url);
    }
}
