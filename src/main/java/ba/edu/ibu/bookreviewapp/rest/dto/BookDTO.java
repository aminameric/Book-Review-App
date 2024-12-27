package ba.edu.ibu.bookreviewapp.rest.dto;

public class BookDTO {
    private String title;
    private String author;
    private String readingStatus; // Enum as a String
    private Long categoryId; // For existing categories
    private String categoryName; // For new categories
    private Long userId;

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getReadingStatus() {
        return readingStatus;
    }

    public void setReadingStatus(String readingStatus) {
        this.readingStatus = readingStatus;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Long getUserId() {
        return userId; // Return the actual value
    }

    public void setUserId(Long userId) {
        this.userId = userId; // Properly set the userId
    }
}
