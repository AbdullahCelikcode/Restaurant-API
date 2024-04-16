package cod.restaurantapi.category.model.mapper;

import cod.restaurantapi.common.model.mapper.BaseMapper;
import cod.restaurantapi.category.controller.response.CategoryResponse;
import cod.restaurantapi.category.service.domain.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryToCategoryResponse extends BaseMapper<Category, CategoryResponse> {
    CategoryToCategoryResponse INSTANCE = Mappers.getMapper(CategoryToCategoryResponse.class);
}
