package cod.restaurantapi.product.service.command;

import cod.restaurantapi.product.model.enums.CategoryStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CategoryUpdateCommand {
    private String name;
    private CategoryStatus status;
}
