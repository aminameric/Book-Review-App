package ba.edu.ibu.bookreviewapp;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookReviewAppApplication {

    public static void main(String[] args) {
        // Load the API key from environment variables if present (Render setup)
        String googleBooksApiKey = System.getenv("GOOGLE_BOOKS_API_KEY");

        // If not present, try loading from the .env file (for local development)
        if (googleBooksApiKey == null || googleBooksApiKey.isEmpty()) {
            Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
            googleBooksApiKey = dotenv.get("GOOGLE_BOOKS_API_KEY");
        }

        // Set the property if the key was found
        if (googleBooksApiKey != null && !googleBooksApiKey.isEmpty()) {
            System.setProperty("GOOGLE_BOOKS_API_KEY", googleBooksApiKey);
        } else {
            throw new RuntimeException("Google Books API Key is missing!");
        }

        SpringApplication.run(BookReviewAppApplication.class, args);
    }

}
