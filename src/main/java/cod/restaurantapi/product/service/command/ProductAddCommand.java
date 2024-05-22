package cod.restaurantapi.product.service.command;

import cod.restaurantapi.product.model.enums.ExtentType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class ProductAddCommand {
    private String name;
    private String ingredient;
    private BigDecimal price;
    private BigDecimal extent;
    private ExtentType extentType;
    private Long categoryId;
}
