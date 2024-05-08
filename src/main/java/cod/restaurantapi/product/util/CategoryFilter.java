package cod.restaurantapi.product.util;

import cod.restaurantapi.common.model.RMAFilter;
import cod.restaurantapi.product.model.enums.CategoryStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryFilter implements RMAFilter {

    private String name;

    private Set<CategoryStatus> statuses;

}
