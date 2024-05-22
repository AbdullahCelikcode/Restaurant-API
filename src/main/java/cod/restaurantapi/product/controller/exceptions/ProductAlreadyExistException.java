package cod.restaurantapi.product.controller.exceptions;

import cod.restaurantapi.common.exception.RMAAlreadyExistException;

import java.io.Serial;

public class ProductAlreadyExistException extends RMAAlreadyExistException {
    @Serial
    private static final long serialVersionUID = 1806605873477753936L;

    public ProductAlreadyExistException() {
        super("Product Already Exist");
    }
}
