package com.axreng.backend;

import com.axreng.backend.controller.CrawlController;
import com.axreng.backend.repositories.CrawlRepository;
import com.axreng.backend.repositories.CrawlRepositoryImpl;
import com.axreng.backend.services.CrawlService;
import com.axreng.backend.util.MyRequest;
import com.axreng.backend.util.Util;
import spark.Spark;

import java.util.List;

public class WebCrawler {

    public static void main(String[] args) {
        String baseUrl = System.getenv("BASE_URL");
        MyRequest myRequest = new MyRequest();
        Util util = new Util();
        CrawlRepository crawlRepository = new CrawlRepositoryImpl();
        CrawlService crawlService = new CrawlService(crawlRepository, myRequest, util);

        CrawlController crawlController = new CrawlController(crawlService);
        crawlController.initializeRoutes(baseUrl);

        Spark.port(4567);
        Spark.awaitInitialization();

        System.out.println("Server started on port 4567");
    }
}
