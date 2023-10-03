import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.axreng.backend.domain.CrawlResult;
import com.axreng.backend.repositories.CrawlRepository;
import com.axreng.backend.services.CrawlService;
import com.axreng.backend.util.MyRequest;
import com.axreng.backend.util.Util;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

public class CrawlServiceTest {
    private CrawlService crawlService;
    private CrawlRepository crawlRepository;
    private MyRequest myRequest;
    private Util util;

    @Test
    public void testInitiateCrawlWithValidKeyword() {
        // Arrange
        CrawlRepository crawlRepository = mock(CrawlRepository.class);
        MyRequest myRequest = mock(MyRequest.class);
        Util util = mock(Util.class);
        CrawlService crawlService = new CrawlService(crawlRepository, myRequest, util);

        when(util.generateID()).thenReturn("xsIpUl9I");
        when(myRequest.sendPostRequest("security")).thenReturn(List.of("xsIpUl9I"));

        // Act
        List<String> searchIds = crawlService.initiateCrawl("security");

        // Assert
        assertEquals(1, searchIds.size());
        assertEquals("xsIpUl9I", searchIds.get(0));

        // Verify
        verify(util).generateID();
        verify(myRequest).sendPostRequest("security");
    }
    @Test
    public void testFindUrlsWithKeyword() {
        CrawlRepository crawlRepository = mock(CrawlRepository.class);
        MyRequest myRequest = mock(MyRequest.class);
        Util util = mock(Util.class);
        CrawlService crawlService = new CrawlService(crawlRepository, myRequest, util);

        String htmlContent = "<a href=\"http://hiring.axreng.com/htmlman2/ptrace.2.html\"></a>" +
                "<a href=\"http://hiring.axreng.com/htmlman2/setuid.2.html\"></a>";

        List<String> foundUrls = crawlService.findUrlsWithKeyword(htmlContent, "http://hiring.axreng.com/");

        assertEquals(2, foundUrls.size());
        assertTrue(foundUrls.contains("http://hiring.axreng.com/htmlman2/ptrace.2.html"));
        assertTrue(foundUrls.contains("http://hiring.axreng.com/htmlman2/setuid.2.html"));
    }
}
