package cod.restaurantapi.diningtable.controller.request;

import cod.restaurantapi.common.controller.request.RMAListRequest;
import cod.restaurantapi.common.model.RMAFilter;
import cod.restaurantapi.diningtable.model.enums.DiningTableStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.AssertTrue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class DiningTableListRequest extends RMAListRequest {

    private DiningTableFilter filter;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DiningTableFilter implements RMAFilter {
        private Integer size;
        private Set<DiningTableStatus> status;
    }

    @JsonIgnore
    @AssertTrue
    @Override
    public boolean isOrderPropertyAccepted() {
        final Set<String> acceptedFilterFields = Set.of("status", "id", "size");
        return this.isPropertyAccepted(acceptedFilterFields);
    }

}
