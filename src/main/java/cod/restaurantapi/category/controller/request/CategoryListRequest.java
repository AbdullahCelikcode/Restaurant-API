package cod.restaurantapi.category.controller.request;

import cod.restaurantapi.common.util.Pagination;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Builder
@Setter

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
