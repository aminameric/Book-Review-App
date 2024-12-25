package ba.edu.ibu.bookreviewapp;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookReviewAppApplication {

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure().load();
        System.setProperty("GOOGLE_BOOKS_API_KEY", dotenv.get("GOOGLE_BOOKS_API_KEY"));
        SpringApplication.run(BookReviewAppApplication.class, args);
    }

}
