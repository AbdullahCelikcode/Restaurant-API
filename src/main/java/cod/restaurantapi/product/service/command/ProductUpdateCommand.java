package cod.restaurantapi.product.service.command;

import cod.restaurantapi.product.model.enums.ExtentType;
import cod.restaurantapi.product.model.enums.ProductStatus;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class ProductUpdateCommand {

    private String name;
    private String ingredient;
    private ProductStatus status;
    private BigDecimal price;
    private BigDecimal extent;
    private ExtentType extentType;
    private Long categoryId;

}
