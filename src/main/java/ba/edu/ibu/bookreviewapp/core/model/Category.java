package ba.edu.ibu.bookreviewapp.core.model;

import jakarta.persistence.*;

@Entity(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true) // Ensure unique names
    private String name;

    // Constructors
    public Category(String name) {
        this.name = name;
    }

    public Category() {}

    // Getters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name; // Properly set the name
    }
}
