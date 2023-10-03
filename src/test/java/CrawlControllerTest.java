import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static spark.Spark.awaitInitialization;
import static spark.Spark.stop;

import com.axreng.backend.controller.CrawlController;
import com.axreng.backend.services.CrawlService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.Spark;
import spark.utils.IOUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static spark.Spark.*;

public class CrawlControllerTest {

    @BeforeEach
    public void setUp() {
        CrawlService crawlService = mock(CrawlService.class);
        CrawlController crawlController = new CrawlController(crawlService);
        crawlController.initializeRoutes("http://testapp.axreng.com:4567");
        awaitInitialization();
    }

    @AfterAll
    public static void tearDown() {
        stop();
    }

    @Test
    public void testPostEndpoint() throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL("http://testapp.axreng.com:4567/crawl").openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        String requestBody = "{\"keyword\":\"example\"}";

        try (OutputStream os = connection.getOutputStream()) {
            os.write(requestBody.getBytes("UTF-8"));

            int responseCode = connection.getResponseCode();
            assertEquals(200, responseCode);

            String responseBody = IOUtils.toString(connection.getInputStream());
            System.out.println(responseBody);
        }
    }

    @Test
    public void testGetEndpoint() throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL("http://testapp.axreng.com:4567/crawl/xsIpUl9I").openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        assertEquals(200, responseCode);

        String responseBody = IOUtils.toString(connection.getInputStream());
        System.out.println(responseBody);
    }
}
