package cod.restaurantapi.category.service.command;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryCreateCommand {
    private String name;
}
