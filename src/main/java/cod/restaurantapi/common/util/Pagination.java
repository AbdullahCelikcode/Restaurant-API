package cod.restaurantapi.common.util;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Range;


@Builder
public class Pagination {

    @NotNull
    @Positive
    @Range(max = Integer.MAX_VALUE)
    private int pageNumber;

    @Getter
    @NotNull
    @Positive
    @Range(max = Integer.MAX_VALUE)
    private int pageSize;

    public int getPageNumber() {
        return this.pageNumber - 1;
    }

}
