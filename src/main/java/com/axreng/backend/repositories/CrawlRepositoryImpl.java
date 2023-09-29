package com.axreng.backend.repositories;

import com.axreng.backend.domain.CrawlResult;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class CrawlRepositoryImpl implements CrawlRepository {
    private final Map<String, CrawlResult> crawlResults = new ConcurrentHashMap<>();

    @Override
    public String initiateCrawl(String keyword) {
        String searchId = generateSearchId();
        CrawlResult crawlResult = new CrawlResult(searchId, keyword);
        crawlResults.put(searchId, crawlResult);
        return searchId;
    }

    @Override
    public CrawlResult getCrawlResult(String searchId) {
        return crawlResults.get(searchId);
    }

    private String generateSearchId() {
        UUID uuid = UUID.randomUUID();
        String searchId = uuid.toString().replace("-", "");

        return searchId;
    }
}
