import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.axreng.backend.domain.CrawlResult;
import com.axreng.backend.repositories.CrawlRepository;
import com.axreng.backend.services.CrawlService;
import com.axreng.backend.util.MyRequest;
import com.axreng.backend.util.Util;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class CrawlServiceTest {
    private CrawlService crawlService;
    private CrawlRepository crawlRepository;
    private MyRequest myRequest;
    private Util util;

    @BeforeEach
    public void setUp() {
        crawlRepository = Mockito.mock(CrawlRepository.class);
        myRequest = Mockito.mock(MyRequest.class);
        util = Mockito.mock(Util.class);
        crawlService = new CrawlService(crawlRepository, myRequest, util);
    }

    @AfterAll
    public void tearDown() {
    }

    @Test
    public void testInitiateCrawl() {
        when(util.generateID()).thenReturn("searchId123");
        when(myRequest.sendPostRequest("example")).thenReturn(List.of("result1", "result2"));

        List<String> searchIds = crawlService.initiateCrawl("example");

        assertEquals(1, searchIds.size());
        assertEquals("searchId123", searchIds.get(0));

        verify(util).generateID();
        verify(myRequest).sendPostRequest("example");
    }

    @Test
    public void testGetCrawlResult() {
        String baseUrl = "http://hiring.axreng.com/";
        CrawlResult result = crawlService.getCrawlResult("exampleSearchId", baseUrl);
        MatcherAssert.assertThat(result, Matchers.notNullValue());
    }
}
