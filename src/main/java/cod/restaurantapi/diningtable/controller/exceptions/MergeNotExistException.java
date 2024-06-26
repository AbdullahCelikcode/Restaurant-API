package cod.restaurantapi.diningtable.controller.exceptions;

import cod.restaurantapi.common.exception.RMANotFoundException;

import java.io.Serial;

public class MergeNotExistException extends RMANotFoundException {
    @Serial
    private static final long serialVersionUID = 7816289468982776792L;

    public MergeNotExistException() {
        super("Merge Id Not Exist");
    }
}
