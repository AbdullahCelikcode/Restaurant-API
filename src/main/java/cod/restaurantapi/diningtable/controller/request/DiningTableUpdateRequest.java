package cod.restaurantapi.diningtable.controller.request;

import cod.restaurantapi.diningtable.model.enums.DiningTableStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiningTableUpdateRequest {

    @NotNull
    @Range(min = 0, max = 100)
    private Integer size;

    @NotNull
    private DiningTableStatus status;
}
