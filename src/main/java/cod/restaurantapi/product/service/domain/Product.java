package cod.restaurantapi.product.service.domain;

import cod.restaurantapi.product.model.enums.ExtentType;
import cod.restaurantapi.product.model.enums.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

    private UUID id;

    private String name;

    private String ingredient;

    private double price;

    private ProductStatus status;

    private double extent;

    private ExtentType extentType;

    private long categoryId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public void active() {
        this.status = ProductStatus.ACTIVE;
    }
}
