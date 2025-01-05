package ba.edu.ibu.bookreviewapp.rest.dto;

public class UserBookDTO {
    private Long id; // ID for the user-books entry
    private String content; // Review content
    private Float rating; // Rating of the book
    private Long userId; // Reference to the user
    private Long bookId; // Reference to the book

    public UserBookDTO(long userId, long bookId, String content, float rating) {
        this.userId = userId;
        this.bookId = bookId;
        this.content = content;
        this.rating = rating;
    }


    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

}
