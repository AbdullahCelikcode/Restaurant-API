package cod.restaurantapi.common.exception.model;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatusCode;

import java.time.LocalDateTime;
import java.util.Map;

@Builder
@Getter
public class RMASubErrors {

    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();

    private HttpStatusCode status;

    private Map<String, String> massage;
}
