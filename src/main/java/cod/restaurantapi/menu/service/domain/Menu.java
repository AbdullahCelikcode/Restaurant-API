package cod.restaurantapi.menu.service.domain;

import cod.restaurantapi.product.model.enums.ExtentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Builder
@Setter
@AllArgsConstructor
public class Menu {


    private Product product;

    private Category category;


    @Getter
    @Builder
    public static class Category {
        private Long id;
        private String name;
    }

    @Getter
    @Builder
    public static class Product {
        private UUID id;

        private String name;

        private String ingredient;

        private BigDecimal price;

        private String currency;

        private Integer extent;

        private ExtentType extentType;
    }
}