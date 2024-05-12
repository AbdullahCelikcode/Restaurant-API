package cod.restaurantapi.common.controller.request;

import cod.restaurantapi.common.model.Pagination;
import cod.restaurantapi.common.model.Sorting;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public abstract class RMAListRequest {

    @NotNull
    @Valid
    private Pagination pagination;

    private Sorting sorting;

}
