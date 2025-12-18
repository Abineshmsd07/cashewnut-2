package Entity;



import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private BigDecimal price; // Always use BigDecimal for money, never Double!

    @Column(nullable = false)
    private Integer stockQuantity;

    private String imageUrl;

    // Many Products -> One Category
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = false) // This creates the Foreign Key column
    private Category category;
}
