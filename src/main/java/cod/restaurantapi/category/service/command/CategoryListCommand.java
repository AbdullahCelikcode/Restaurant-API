package cod.restaurantapi.category.service.command;

import cod.restaurantapi.common.util.Pagination;
import cod.restaurantapi.category.controller.request.CategoryListRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CategoryListCommand {

    private Pagination pagination;

    private CategoryListRequest.CategoryFilter filter;


}
