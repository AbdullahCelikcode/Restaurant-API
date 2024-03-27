package cod.restaurantapi.common.exception.model;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatusCode;

import java.time.LocalDateTime;

@Getter
@Builder
public class RMAError {
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();

    private HttpStatusCode status;

    private String massage;

}
