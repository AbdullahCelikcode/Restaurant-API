package cod.restaurantapi.category.controller.response;

import cod.restaurantapi.category.controller.request.CategoryListRequest;
import cod.restaurantapi.category.service.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryListResponse {
    private List<Category> categoryList;

    private Integer pageNumber;


    private Integer pageSize;

    private Integer totalPageCount;

    private Long totalElementCount;

    private CategoryListRequest.CategoryFilter filteredBy;
}
