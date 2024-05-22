package cod.restaurantapi.diningtable.controller.exceptions;

import cod.restaurantapi.common.exception.RMANotFoundException;

import java.io.Serial;

public class DiningTableNotExistException extends RMANotFoundException {
    @Serial
    private static final long serialVersionUID = -5392313817027805035L;

    public DiningTableNotExistException() {
        super("Dining Table Not Exist");
    }
}
