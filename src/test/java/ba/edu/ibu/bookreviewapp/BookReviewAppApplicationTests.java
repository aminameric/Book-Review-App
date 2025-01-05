package ba.edu.ibu.bookreviewapp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = "googlebooks.secret=test-api-key")
class BookReviewAppApplicationTests {

    @Test
    void contextLoads() {
    }

}
