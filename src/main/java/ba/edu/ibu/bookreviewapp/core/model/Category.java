package ba.edu.ibu.bookreviewapp.core.model;

import jakarta.persistence.*;
import java.util.List;

@Entity(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;


    public Category(String name) {
        this.name = name;
    }

    public Category() {}

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
