package cod.restaurantapi.product.controller.request;

import cod.restaurantapi.product.util.CategoryFilter;
import cod.restaurantapi.common.util.Pagination;
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

    private CategoryFilter filter;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CategoryFilter {
        private String name;

    }
}
