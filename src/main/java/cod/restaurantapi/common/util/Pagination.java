package cod.restaurantapi.common.util;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Range;

@Getter
@Builder
public class Pagination {

    @NotNull
    @Positive
    @Range(max = Integer.MAX_VALUE)
    int pageNumber;

    @NotNull
    @Positive
    @Range(max = Integer.MAX_VALUE)
    int pageSize;
}
