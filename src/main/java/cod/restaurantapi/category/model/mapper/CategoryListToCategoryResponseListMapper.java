package cod.restaurantapi.category.model.mapper;

import cod.restaurantapi.common.model.mapper.BaseMapper;
import cod.restaurantapi.category.controller.response.CategoryListResponse;
import cod.restaurantapi.category.service.domain.CategoryList;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryListToCategoryResponseListMapper extends BaseMapper<CategoryList, CategoryListResponse> {
    CategoryListToCategoryResponseListMapper INSTANCE = Mappers.getMapper(CategoryListToCategoryResponseListMapper.class);
}
