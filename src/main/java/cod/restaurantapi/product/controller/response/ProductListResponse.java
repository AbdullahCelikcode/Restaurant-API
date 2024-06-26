package cod.restaurantapi.product.controller.response;

import cod.restaurantapi.product.model.enums.ExtentType;
import cod.restaurantapi.product.model.enums.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class ProductListResponse {

    private UUID id;
    private String name;
    private String ingredient;
    private BigDecimal price;
    private ProductStatus status;
    private BigDecimal extent;
    private ExtentType extentType;
    private Category category;
    private String currency;

    @Getter
    @Builder
    public static class Category {
        private Long id;
        private String name;

    }

}


