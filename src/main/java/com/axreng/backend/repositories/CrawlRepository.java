package com.axreng.backend.repositories;

import com.axreng.backend.domain.CrawlResult;

public interface CrawlRepository {
    String initiateCrawl(String keyword);
    CrawlResult getCrawlResult(String searchId);
}
