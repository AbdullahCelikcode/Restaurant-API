package cod.restaurantapi.common.exception;

import java.io.Serial;

public class RMAStatusAlreadyChangedException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -8884890793455522866L;

    public RMAStatusAlreadyChangedException() {
        super("Status already changed");
    }
}

