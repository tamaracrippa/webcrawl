package com.axreng.backend.repositories;

import com.axreng.backend.domain.CrawlResult;

public interface CrawlRepository {
    CrawlResult getCrawlResult(String searchId);
    void  saveCrawlResult(String searchId, CrawlResult crawlResult);

    }
