package cod.restaurantapi.common.exception;

import java.io.Serial;

public class RMANotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 3715381831905269072L;


    public RMANotFoundException(String message) {
        super(message);
    }
}
