package cod.restaurantapi.category.service.command;

import cod.restaurantapi.category.model.enums.CategoryStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryUpdateCommand {
    private String name;
    private CategoryStatus status;
}
