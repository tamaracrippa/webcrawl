package com.axreng.backend.repositories;

import com.axreng.backend.domain.CrawlResult;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class CrawlRepositoryImpl implements CrawlRepository {
    private final Map<String, CrawlResult> crawlResults = new ConcurrentHashMap<>();

    @Override
    public CrawlResult getCrawlResult(String searchId) {
        return crawlResults.get(searchId);
    }

    @Override
    public void saveCrawlResult(String searchId, CrawlResult crawlResult) {
        crawlResults.put(searchId, crawlResult);
    }
}
