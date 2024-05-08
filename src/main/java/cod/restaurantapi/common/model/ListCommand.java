package cod.restaurantapi.common.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.domain.PageRequest;


@Setter
@Getter
@SuperBuilder
public abstract class ListCommand {

    private Pagination pagination;


    private Sorting sorting;

    public PageRequest toPageable() {
        return PageRequest.of((pagination.getPageNumber() - 1), pagination.getPageSize(), sorting.direction, sorting.property);

    }

}

