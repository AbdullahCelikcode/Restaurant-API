package cod.restaurantapi.diningtable.controller.exceptions;

import cod.restaurantapi.common.exception.RMAAlreadyExistException;

import java.io.Serial;

public class DiningTableAlreadySplitException extends RMAAlreadyExistException {
    @Serial
    private static final long serialVersionUID = -7754477762155902306L;

    public DiningTableAlreadySplitException() {
        super("Merge Id Already Split");
    }
}
