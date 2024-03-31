package exercise.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

import static jakarta.persistence.GenerationType.IDENTITY;

// BEGIN
@Entity
@EqualsAndHashCode(of = {"title", "price"})
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String title;
    private Integer price;
}
// END
