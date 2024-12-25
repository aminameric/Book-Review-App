package ba.edu.ibu.bookreviewapp.core.model;

import jakarta.persistence.*;
import java.util.List;

@Entity(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String author;

    @Enumerated(EnumType.STRING)
    private ReadingStatus readingStatus;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Review> reviews;

    public Book(String title, String author, ReadingStatus readingStatus, Category category, User user) {
        this.title = title;
        this.author = author;
        this.readingStatus = readingStatus;
        this.category = category;
        this.user = user;
    }

    protected Book() {
    }

    public enum ReadingStatus {
        NOT_STARTED, IN_PROGRESS, COMPLETED
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public ReadingStatus getReadingStatus() {
        return readingStatus;
    }

    public Category getCategory() {
        return category;
    }

    public User getUser() {
        return user;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setReadingStatus(ReadingStatus readingStatus) {
        this.readingStatus = readingStatus;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

}
