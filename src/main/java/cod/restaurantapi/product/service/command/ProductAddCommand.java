package cod.restaurantapi.product.service.command;

import cod.restaurantapi.product.model.enums.ExtentType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductAddCommand {
    private String name;
    private String ingredient;
    private double price;
    private double extent;
    private ExtentType extentType;
    private long categoryId;
}
