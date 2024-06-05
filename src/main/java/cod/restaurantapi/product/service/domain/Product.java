package cod.restaurantapi.product.service.domain;

import cod.restaurantapi.category.service.domain.Category;
import cod.restaurantapi.product.model.enums.ExtentType;
import cod.restaurantapi.product.model.enums.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class Product {

    private UUID id;
    private String name;
    private String ingredient;
    private BigDecimal price;
    private ProductStatus status;
    private BigDecimal extent;
    private ExtentType extentType;
    private long categoryId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Category category;

    public void active() {
        this.status = ProductStatus.ACTIVE;
    }
}
