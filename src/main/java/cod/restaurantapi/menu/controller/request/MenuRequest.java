package cod.restaurantapi.menu.controller.request;

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
@NoArgsConstructor
public class MenuRequest extends RMAListRequest {


    private MenuFilter filter;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MenuFilter implements RMAFilter {

        private String name;

    }

    @JsonIgnore
    @AssertTrue
    @Override
    public boolean isOrderPropertyAccepted() {
        final Set<String> acceptedFilterFields = Set.of();
        return this.isPropertyAccepted(acceptedFilterFields);
    }


}
