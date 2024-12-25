package ba.edu.ibu.bookreviewapp.core.model;

import jakarta.persistence.*;

@Entity(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private Float rating;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Review(String content, Float rating, Book book, User user) {
        this.content = content;
        this.rating = rating;
        this.book = book;
        this.user = user;
    }

    protected Review() {}

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public Float getRating() {
        return rating;
    }

    public Book getBook() {
        return book;
    }

    public User getUser() {
        return user;
    }
}

