package Repository;



import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // Custom query to find products by category ID
    List<Product> findByCategoryId(Long categoryId);
}