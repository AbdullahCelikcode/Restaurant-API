package cod.restaurantapi.category.controller.exceptions;

import cod.restaurantapi.common.exception.RMAAlreadyExistException;

import java.io.Serial;

public class CategoryAlreadyExistException extends RMAAlreadyExistException {
    @Serial
    private static final long serialVersionUID = -4563821354504432562L;

    public CategoryAlreadyExistException() {
        super("Category Already Exist");
    }
}
