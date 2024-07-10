package cod.restaurantapi.common.exception;

import java.io.Serial;

public class RMABadRequest extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 2796200947207451311L;

    public RMABadRequest(String message) {
        super(message);
    }
}
