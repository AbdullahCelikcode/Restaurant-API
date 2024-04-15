package cod.restaurantapi.product.controller.exceptions;

import cod.restaurantapi.common.exception.RMANotFoundException;

import java.io.Serial;

public class CategoryNotFoundException extends RMANotFoundException {

    @Serial
    private static final long serialVersionUID = 1072149719418962220L;

    public CategoryNotFoundException() {

        super("Category is not exists");
    }


}
