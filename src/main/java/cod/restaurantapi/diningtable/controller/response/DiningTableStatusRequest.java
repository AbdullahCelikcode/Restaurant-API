package cod.restaurantapi.diningtable.controller.response;

import cod.restaurantapi.diningtable.model.enums.DiningTableStatus;
import jakarta.validation.constraints.NotEmpty;

public class DiningTableStatusRequest {

    @NotEmpty
    private DiningTableStatus status;
}
