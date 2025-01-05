package ba.edu.ibu.bookreviewapp.rest.dto;

public class BookDTO {
    private String title;
    private String author;
    private String readingStatus; // Enum as a String
    private Long categoryId; // For existing categories
    private String categoryName; // For new categories
    private Long userId;

    // Getters
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

    // Setters with proper field assignment
    public void setTitle(String title) {
        this.title = title;  // Fixed
    }

    public void setAuthor(String author) {
        this.author = author;  // Fixed
    }

    public void setReadingStatus(String readingStatus) {
        this.readingStatus = readingStatus;  // Fixed
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;  // Fixed
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;  // Fixed
    }

    public void setUserId(Long userId) {
        this.userId = userId;  // Fixed
    }
}
