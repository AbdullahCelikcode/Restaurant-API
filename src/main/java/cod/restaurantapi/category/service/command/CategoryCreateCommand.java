package cod.restaurantapi.category.service.command;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CategoryCreateCommand {
    private String name;
}
