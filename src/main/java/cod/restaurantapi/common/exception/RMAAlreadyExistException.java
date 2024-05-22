package cod.restaurantapi.common.exception;

import java.io.Serial;

public class RMAAlreadyExistException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 3200930125169804296L;

    public RMAAlreadyExistException(String message) {
        super(message);
    }
}
