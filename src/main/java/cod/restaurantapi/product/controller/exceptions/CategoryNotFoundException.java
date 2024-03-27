package cod.restaurantapi.product.controller.exceptions;

import java.io.Serial;

public class CategoryNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1072149719418962220L;

    public CategoryNotFoundException() {

        super("Category is not exists");
    }


}
