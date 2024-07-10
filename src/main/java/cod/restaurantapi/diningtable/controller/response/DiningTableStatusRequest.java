package cod.restaurantapi.diningtable.controller.response;

import cod.restaurantapi.diningtable.model.enums.DiningTableStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiningTableStatusRequest {

    @NotNull
    private DiningTableStatus status;
}
