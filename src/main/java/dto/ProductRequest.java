package dto;



import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductRequest {
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stockQuantity;
    private String imageUrl;
    private Long categoryId; // We only send the ID, not the whole object
}
