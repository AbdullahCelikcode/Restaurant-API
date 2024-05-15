package cod.restaurantapi.product.controller.exceptions;

import cod.restaurantapi.common.exception.RMANotFoundException;

import java.io.Serial;

public class ProductNotFoundException extends RMANotFoundException {

    @Serial
    private static final long serialVersionUID = 5606513521031252514L;

    public ProductNotFoundException() {
        super("Product not found");
    }
}
