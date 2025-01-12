package ba.edu.ibu.bookreviewapp.rest.dto;

import java.util.List;

public class BookDTO {
    private String title;
    private String author;
    private String readingStatus; // Enum as a String
    private Long categoryId; // For existing categories
    private String categoryName; // For new categories
    private Long userId;
    private List<UserBookDTO> userBooks;  // ✅ Added for reviews

    // Getters and Setters for all fields including userBooks
    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getReadingStatus() {
        return readingStatus;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public Long getUserId() {
        return userId;
    }

    public List<UserBookDTO> getUserBooks() {
        return userBooks;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setReadingStatus(String readingStatus) {
        this.readingStatus = readingStatus;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setUserBooks(List<UserBookDTO> userBooks) {
        this.userBooks = userBooks;
    }
}
