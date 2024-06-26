package cod.restaurantapi.product.service.domain;

import cod.restaurantapi.category.service.domain.Category;
import cod.restaurantapi.product.model.enums.ExtentType;
import cod.restaurantapi.product.model.enums.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
    private Long categoryId;
    private Category category;
    private String currency;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public void active() {
        this.status = ProductStatus.ACTIVE;
    }

    public static class ProductBuilder {
        private ProductBuilder() {
        }

        public ProductBuilder price(BigDecimal price) {
            this.price = price.setScale(2, RoundingMode.UP);
            return this;
        }
    }


}
