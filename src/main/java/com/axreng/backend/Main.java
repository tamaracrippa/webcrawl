package com.axreng.backend;

import com.axreng.backend.controller.CrawlController;
import com.axreng.backend.repositories.CrawlRepository;
import com.axreng.backend.repositories.CrawlRepositoryImpl;
import com.axreng.backend.services.CrawlService;
import com.axreng.backend.util.MyRequest;
import com.axreng.backend.util.Util;
import spark.Spark;

public class Main {

    public static void main(String[] args) {
        String baseUrl = System.getenv("BASE_URL");
        MyRequest myRequest = new MyRequest();
        Util util = new Util();
        CrawlRepository crawlRepository = new CrawlRepositoryImpl();
        CrawlService crawlService = new CrawlService(crawlRepository, myRequest, util);

        CrawlController crawlController = new CrawlController(crawlService);
        crawlController.initializeRoutes(baseUrl);

        String host = "testapp.axreng.com";
        int port = 4567;

        Spark.ipAddress(host);
        Spark.port(port);

        Spark.awaitInitialization();

        System.out.println("Server started on " + host + ":" + port);
    }
}
