package cod.restaurantapi.product.controller.request;

import cod.restaurantapi.common.model.Pagination;
import cod.restaurantapi.common.model.Sorting;
import cod.restaurantapi.product.util.CategoryFilter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryListRequest {

    @NotNull
    @Valid
    private Pagination pagination;

    private Sorting sorting;

    private CategoryFilter filter;

}
