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

    public String getAuthor() {
        return author;
    }

    public String getReadingStatus() {
        return readingStatus;
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

    public Long getUserId() {
        return userId; // Return the actual value
    }

    public void setUserId(Long userId) {
        this.userId = userId; // Properly set the userId
    }
}
