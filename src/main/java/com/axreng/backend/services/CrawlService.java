package com.axreng.backend.services;

import com.axreng.backend.domain.CrawlResult;
import com.axreng.backend.repositories.CrawlRepository;

public class CrawlService {
    private final CrawlRepository crawlRepository;

    public CrawlService(CrawlRepository crawlRepository) {
        this.crawlRepository = crawlRepository;
    }

    public String initiateCrawl(String keyword) {
        if (isValidKeyword(keyword)) {
            String searchId = crawlRepository.initiateCrawl(keyword);
            return searchId;
        } else {
            throw new IllegalArgumentException("O termo fornecido não atende aos requisitos.");
        }
    }

    public CrawlResult getCrawlResult(String searchId) {
        return crawlRepository.getCrawlResult(searchId);
    }

    private boolean isValidKeyword(String keyword) {
        return keyword != null && keyword.length() >= 4 && keyword.length() <= 32;
    }
}
