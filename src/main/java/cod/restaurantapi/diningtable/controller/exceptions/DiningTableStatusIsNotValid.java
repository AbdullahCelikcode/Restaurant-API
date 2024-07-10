package cod.restaurantapi.diningtable.controller.exceptions;

import cod.restaurantapi.common.exception.RMABadRequest;

import java.io.Serial;

public class DiningTableStatusIsNotValid extends RMABadRequest {
    @Serial
    private static final long serialVersionUID = 7199342435553597838L;

    public DiningTableStatusIsNotValid() {
        super("Dining table status is not accepted");
    }
}
