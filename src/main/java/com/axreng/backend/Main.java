package com.axreng.backend;

import com.axreng.backend.controller.CrawlController;
import com.axreng.backend.repositories.CrawlRepository;
import com.axreng.backend.repositories.CrawlRepositoryImpl;
import com.axreng.backend.services.CrawlService;
import spark.Spark;

public class Main {
    public static void main(String[] args) {
        CrawlRepository crawlRepository = new CrawlRepositoryImpl();
        CrawlService crawlService = new CrawlService(crawlRepository);

        CrawlController crawlController = new CrawlController(crawlService);
        crawlController.initializeRoutes();

        Spark.port(4567);
        Spark.awaitInitialization();

        System.out.println("Server started on port 4567");
    }
}