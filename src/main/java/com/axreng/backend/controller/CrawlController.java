package com.axreng.backend.controller;

import com.axreng.backend.domain.CrawlResult;
import com.axreng.backend.services.CrawlService;
import com.axreng.backend.util.MyRequest;
import com.axreng.backend.util.MyResponse;
import com.google.gson.Gson;
import spark.Spark;

public class CrawlController {

    private final CrawlService crawlService;
    private static final Gson gson = new Gson();

    public CrawlController(CrawlService crawlService) {
        this.crawlService = crawlService;
    }

    public void initializeRoutes(String baseUrl) {

        Spark.post("/crawl", (request, response) -> {
            response.type("application/json");

            MyRequest parsedRequest = gson.fromJson(request.body(), MyRequest.class);
            String keyword = parsedRequest.getKeyword(); // Obtenha a palavra-chave fornecida pelo usuário

            if (keyword.length() < 4 || keyword.length() > 32) {
                response.status(400); // Bad Request
                MyResponse errorResponse = new MyResponse("O termo de busca deve ter entre 4 e 32 caracteres.");
                return gson.toJson(errorResponse);
            }

            String searchId = crawlService.initiateCrawl(keyword).toString(); // Use a palavra-chave fornecida pelo usuário

            response.status(200); // OK
            MyResponse successResponse = new MyResponse(searchId);
            return gson.toJson(successResponse);
        });

        Spark.get("/crawl/:id", (request, response) -> {
            response.type("application/json");

            String searchId = request.params(":id");

            CrawlResult crawlResult = crawlService.getCrawlResult(searchId, baseUrl);

            if (crawlResult != null) {
                response.status(200); // OK
                return gson.toJson(crawlResult);
            } else {
                response.status(404); // Not Found
                MyResponse errorResponse = new MyResponse("Busca não encontrada.");
                return gson.toJson(errorResponse);
            }
        });
    }
}
