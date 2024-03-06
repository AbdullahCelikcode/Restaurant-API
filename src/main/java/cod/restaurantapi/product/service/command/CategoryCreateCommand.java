package cod.restaurantapi.product.service.command;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryCreateCommand {
    public String name;
}
