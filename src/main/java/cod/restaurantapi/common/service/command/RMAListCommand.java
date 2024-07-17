package cod.restaurantapi.common.service.command;

import cod.restaurantapi.common.model.Pagination;
import cod.restaurantapi.common.model.Sorting;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.domain.PageRequest;


@Getter
@Setter
@SuperBuilder
public abstract class RMAListCommand {

    private Pagination pagination;

    private Sorting sorting;

    public PageRequest toPageable() {
        if (this.sorting == null) {
            return PageRequest.of(pagination.getPageNumber() - 1, pagination.getPageSize());
        }

        return PageRequest.of((pagination.getPageNumber() - 1), pagination.getPageSize(), sorting.direction, sorting.property);

    }

}

