import com.axreng.backend.util.Util;
import org.junit.jupiter.api.*;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UtilTest {
    private Util util;

    @BeforeAll
    public void setUp() {
        util = new Util();
    }

    @Test
    public void testGenerateID() {
        String generatedID = util.generateID();
        assertNotNull(generatedID, "O ID gerado não deve ser nulo");
    }

}
