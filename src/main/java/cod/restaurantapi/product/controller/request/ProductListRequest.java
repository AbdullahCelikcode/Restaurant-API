package cod.restaurantapi.product.controller.request;

import cod.restaurantapi.common.controller.request.RMAListRequest;
import cod.restaurantapi.common.model.RMAFilter;
import cod.restaurantapi.product.model.enums.ProductStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ProductListRequest extends RMAListRequest {

    @Valid
    private ProductFilter filter;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductFilter implements RMAFilter {

        private String name;

        private Set<ProductStatus> statuses;

        @Positive
        private Long categoryId;

        @Positive
        private Integer minPrice;

        @Positive
        private Integer maxPrice;


    }
}
