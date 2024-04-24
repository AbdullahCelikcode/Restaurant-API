package cod.restaurantapi.product.service.domain;

import cod.restaurantapi.common.util.Sorting;
import cod.restaurantapi.product.util.CategoryFilter;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class CategoryList {

    private List<Category> content;

    private Integer pageNumber;

    private Integer pageSize;

    private Integer totalPageCount;

    private Long totalElementCount;

    private CategoryFilter filteredBy;

    private Sorting sortedBy;

    public Integer getPageNumber() {
        return pageNumber + 1;
    }

}
