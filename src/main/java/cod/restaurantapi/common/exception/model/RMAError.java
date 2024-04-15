package cod.restaurantapi.common.exception.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatusCode;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class RMAError {

    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();

    private HttpStatusCode status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<SubErrors> errors;

    @Getter
    @Builder
    public static class SubErrors {

        private String field;

        private String message;
    }


}
