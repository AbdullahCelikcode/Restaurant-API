package cod.restaurantapi.common.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Range;


@Builder
@Getter
public class Pagination {

    @NotNull
    @Positive
    @Range(max = Integer.MAX_VALUE)
    private int pageNumber;


    @NotNull
    @Positive
    @Range(max = Integer.MAX_VALUE)
    private int pageSize;


}
