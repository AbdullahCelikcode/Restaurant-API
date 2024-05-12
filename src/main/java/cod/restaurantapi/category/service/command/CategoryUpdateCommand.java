package cod.restaurantapi.category.service.command;

import cod.restaurantapi.category.model.enums.CategoryStatus;
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
