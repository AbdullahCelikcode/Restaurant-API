package cod.restaurantapi.category.controller.request;

import cod.restaurantapi.category.model.enums.CategoryStatus;
import cod.restaurantapi.common.controller.request.RMAListRequest;
import cod.restaurantapi.common.model.RMAFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.AssertTrue;
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
public class CategoryListRequest extends RMAListRequest {

    private CategoryFilter filter;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CategoryFilter implements RMAFilter {

        private String name;
        private Set<CategoryStatus> statuses;

    }

    @JsonIgnore
    @AssertTrue
    @Override
    public boolean isOrderPropertyAccepted() {
        final Set<String> acceptedFilterFields = Set.of("id", "name");
        return this.isPropertyAccepted(acceptedFilterFields);
    }

}
